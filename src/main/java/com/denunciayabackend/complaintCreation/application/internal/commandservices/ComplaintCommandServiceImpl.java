package com.denunciayabackend.complaintCreation.application.internal.commandservices;

import com.denunciayabackend.complaintCreation.domain.exceptions.ComplaintNotFoundException;
import com.denunciayabackend.complaintCreation.domain.exceptions.ComplaintValidationException;
import com.denunciayabackend.complaintCreation.domain.exceptions.InvalidComplaintStatusException;
import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.commands.*;
import com.denunciayabackend.complaintCreation.domain.model.entities.Evidence;
import com.denunciayabackend.complaintCreation.domain.model.events.*;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintCommandService;
import com.denunciayabackend.complaintCreation.domain.services.EvidenceService;
import com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories.ComplaintRepository;
import com.denunciayabackend.complaintCreation.interfaces.ComplaintEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ComplaintCommandServiceImpl implements ComplaintCommandService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintEventPublisher eventPublisher;
    private final EvidenceService evidenceService;

    public ComplaintCommandServiceImpl(ComplaintRepository complaintRepository,
                                       ComplaintEventPublisher eventPublisher,
                                       EvidenceService evidenceService) {
        this.complaintRepository = complaintRepository;
        this.eventPublisher = eventPublisher;
        this.evidenceService = evidenceService;
    }

    @Override
    public Complaint handle(CreateComplaintCommand command) {
        validateCreateCommand(command);

        Complaint complaint = new Complaint(command);

        // Guardar la denuncia primero
        Complaint savedComplaint = complaintRepository.save(complaint);

        // Guardar evidencias si existen
        if (command.evidence() != null && !command.evidence().isEmpty()) {
            List<Evidence> evidences = evidenceService.saveEvidencesForComplaint(
                    savedComplaint.getComplaintId(),
                    command.evidence()
            );
            savedComplaint.setEvidences(evidences);
            savedComplaint.addEvidence(command.evidence());
            savedComplaint = complaintRepository.save(savedComplaint);
        }

        eventPublisher.publishComplaintCreatedEvent(new ComplaintCreatedEvent(savedComplaint));
        return savedComplaint;
    }

    @Override
    public Complaint handle(UpdateComplaintCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        validateComplaintForUpdate(complaint);

        complaint.updateComplaint(command);



        Complaint updatedComplaint = complaintRepository.save(complaint);
        eventPublisher.publishComplaintUpdatedEvent(new ComplaintUpdatedEvent(updatedComplaint));
        return updatedComplaint;
    }

    @Override
    public Complaint handle(UpdateComplaintStatusCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        ComplaintStatus previousStatus = complaint.getStatus();
        validateStatusTransition(complaint.getStatus(), command.newStatus());

        complaint.updateStatus(command.newStatus(), command.updateMessage());
        Complaint updatedComplaint = complaintRepository.save(complaint);

        eventPublisher.publishComplaintStatusUpdatedEvent(
                new ComplaintStatusUpdatedEvent(updatedComplaint, previousStatus, command.updateMessage()));
        return updatedComplaint;
    }

    @Override
    public Complaint handle(AssignComplaintCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        if (complaint.getStatus() == ComplaintStatus.REJECTED) {
            throw new InvalidComplaintStatusException("Cannot assign a rejected complaint");
        }

        String responsibleId = command.assignedTo();
        complaint.assignTo(command.assignedTo(), responsibleId);
        Complaint assignedComplaint = complaintRepository.save(complaint);

        eventPublisher.publishComplaintAssignedEvent(new ComplaintAssignedEvent(assignedComplaint));
        return assignedComplaint;
    }

    @Override
    public void handle(DeleteComplaintCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        System.out.println("Deleting complaint with id: " + command.complaintId() + " and status: " + complaint.getStatus());

        // Eliminar evidencias primero
        evidenceService.deleteEvidencesByComplaintId(command.complaintId());

        // Eliminar la denuncia
        complaintRepository.delete(complaint);
        eventPublisher.publishComplaintDeletedEvent(new ComplaintDeletedEvent(complaint));
    }

    @Override
    public void addEvidence(String complaintId, String evidenceUrl) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));

        // Guardar evidencia
        List<Evidence> evidences = evidenceService.saveEvidencesForComplaint(
                complaintId,
                Collections.singletonList(evidenceUrl)
        );

        // Actualizar la queja con la nueva evidencia
        complaint.getEvidences().addAll(evidences);
        complaint.addEvidence(Collections.singletonList(evidenceUrl));
        complaintRepository.save(complaint);
    }

    @Override
    public void updatePriority(String complaintId, String priority) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));

        ComplaintPriority complaintPriority = ComplaintPriority.valueOf(priority.toUpperCase());
        complaint.updatePriority(complaintPriority);
        complaintRepository.save(complaint);
    }

    @Override
    public void assignComplaint(String complaintId, String assignedTo, String responsibleId) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));

        complaint.assignTo(assignedTo, responsibleId);
        complaintRepository.save(complaint);
    }

    @Override
    public Complaint handle(UpdateTimelineItemCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        var timeline = complaint.getTimeline();
        var itemOpt = timeline.stream().filter(t -> t.getId().equals(command.timelineItemId())).findFirst();
        if (itemOpt.isEmpty()) throw new IllegalArgumentException("Timeline item not found");

        var item = itemOpt.get();
        if (command.completed() != null) item.setCompleted(command.completed());
        if (command.current() != null) item.setCurrent(command.current());
        if (command.waitingDecision() != null) item.setWaitingDecision(command.waitingDecision());
        if (command.status() != null) item.setStatus(command.status());
        if (command.updateMessage() != null) item.setUpdateMessage(command.updateMessage());

        Complaint updated = complaintRepository.save(complaint);
        return updated;
    }

    @Override
    public Complaint handle(AdvanceTimelineCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        var timeline = complaint.getTimeline();
        int currentIndex = -1;
        for (int i = 0; i < timeline.size(); i++) if (timeline.get(i).isCurrent()) { currentIndex = i; break; }

        if (currentIndex == -1 || currentIndex >= timeline.size() - 1) return complaint;

        // current -> completed and unset
        timeline.get(currentIndex).setCompleted(true);
        timeline.get(currentIndex).setCurrent(false);

        // next becomes current
        var next = timeline.get(currentIndex + 1);
        next.setCurrent(true);
        if (!next.isWaitingDecision()) next.setCompleted(true);
        next.setDate(java.time.LocalDateTime.now());

        // Update complaint status from timeline
        complaint.setUpdateMessage(next.getUpdateMessage());
        complaintRepository.save(complaint);
        return complaint;
    }

    @Override
    public Complaint handle(AcceptDecisionCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        var timeline = complaint.getTimeline();
        int currentIndex = -1;
        int decisionIndex = -1;
        for (int i = 0; i < timeline.size(); i++) {
            if (timeline.get(i).isCurrent()) currentIndex = i;
            if (timeline.get(i).isWaitingDecision()) decisionIndex = i;
        }

        if (currentIndex == -1 || decisionIndex == -1 || decisionIndex >= timeline.size() - 1) return complaint;

        timeline.get(currentIndex).setCompleted(true);
        timeline.get(currentIndex).setCurrent(false);

        var decision = timeline.get(decisionIndex);
        decision.setCompleted(true);
        decision.setCurrent(false);
        decision.setStatus("Accepted");
        decision.setWaitingDecision(false);
        decision.setDate(java.time.LocalDateTime.now());

        var next = timeline.get(decisionIndex + 1);
        next.setCurrent(true);
        next.setCompleted(true);
        next.setDate(java.time.LocalDateTime.now());

        complaint.setUpdateMessage("Complaint accepted");

        complaintRepository.save(complaint);
        return complaint;
    }

    @Override
    public Complaint handle(RejectDecisionCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        var timeline = complaint.getTimeline();
        int currentIndex = -1;
        int decisionIndex = -1;
        for (int i = 0; i < timeline.size(); i++) {
            if (timeline.get(i).isCurrent()) currentIndex = i;
            if (timeline.get(i).isWaitingDecision()) decisionIndex = i;
        }

        if (currentIndex == -1 || decisionIndex == -1 || decisionIndex >= timeline.size() - 1) return complaint;

        timeline.get(currentIndex).setCompleted(true);
        timeline.get(currentIndex).setCurrent(false);

        var decision = timeline.get(decisionIndex);
        decision.setCompleted(true);
        decision.setCurrent(true);
        decision.setStatus("Rejected");
        decision.setWaitingDecision(false);
        decision.setDate(java.time.LocalDateTime.now());

        var next = timeline.get(decisionIndex + 1);
        next.setCompleted(false);
        next.setCurrent(false);

        complaint.setUpdateMessage("Complaint rejected");

        complaintRepository.save(complaint);
        return complaint;
    }

    private void validateCreateCommand(CreateComplaintCommand command) {
        if (command.description() == null || command.description().isBlank()) {
            throw new ComplaintValidationException("Complaint description cannot be empty");
        }
        if (command.description().length() < 10) {
            throw new ComplaintValidationException("Complaint description must be at least 10 characters long");
        }
    }

    private void validateComplaintForUpdate(Complaint complaint) {
        if (complaint.getStatus() == ComplaintStatus.REJECTED) {
            throw new InvalidComplaintStatusException("Cannot update a rejected complaint");
        }
        if (complaint.getStatus() == ComplaintStatus.COMPLETED) {
            throw new InvalidComplaintStatusException("Cannot update a completed complaint");
        }
    }

    private void validateStatusTransition(ComplaintStatus currentStatus, ComplaintStatus newStatus) {
        if (currentStatus == ComplaintStatus.COMPLETED && newStatus != ComplaintStatus.COMPLETED) {
            throw new InvalidComplaintStatusException("Cannot change status from COMPLETED to another status");
        }
        if (currentStatus == ComplaintStatus.REJECTED && newStatus != ComplaintStatus.REJECTED) {
            throw new InvalidComplaintStatusException("Cannot change status from REJECTED to another status");
        }
    }

    @Override
    public Complaint handle(UpdateTimelineItemStatusCommand command) {
        var complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        // Encontrar el timeline item específico
        var timelineItem = complaint.getTimeline().stream()
                .filter(item -> item.getId().equals(command.timelineItemId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Timeline item not found with id: " + command.timelineItemId()));

        // Actualizar el estado del timeline item
        if (command.completed() != null) {
            timelineItem.setCompleted(command.completed());
        }
        if (command.current() != null) {
            timelineItem.setCurrent(command.current());
        }
        if (command.waitingDecision() != null) {
            timelineItem.setWaitingDecision(command.waitingDecision());
        }
        if (command.updateMessage() != null) {
            timelineItem.setUpdateMessage(command.updateMessage());
        }

        // Actualizar el status del complaint basado en el timeline
        complaint.updateStatusFromTimeline();

        return complaintRepository.save(complaint);
    }
}