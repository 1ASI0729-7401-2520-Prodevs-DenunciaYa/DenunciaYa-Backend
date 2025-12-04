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

        // Actualizar evidencias si se proporcionan
        if (command.evidence() != null) {
            // Eliminar evidencias existentes
            evidenceService.deleteEvidencesByComplaintId(command.complaintId());

            // Guardar nuevas evidencias
            if (!command.evidence().isEmpty()) {
                List<Evidence> evidences = evidenceService.saveEvidencesForComplaint(
                        command.complaintId(),
                        command.evidence()
                );
                complaint.setEvidences(evidences);
                complaint.addEvidence(command.evidence());
            }
        }

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
}