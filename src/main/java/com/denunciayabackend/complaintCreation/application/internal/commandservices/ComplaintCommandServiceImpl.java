package com.denunciayabackend.complaintCreation.application.internal.commandservices;

import com.denunciayabackend.complaintCreation.domain.exceptions.ComplaintNotFoundException;
import com.denunciayabackend.complaintCreation.domain.exceptions.ComplaintValidationException;
import com.denunciayabackend.complaintCreation.domain.exceptions.InvalidComplaintStatusException;
import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.commands.*;
import com.denunciayabackend.complaintCreation.domain.model.events.*;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintCommandService;
import com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories.ComplaintRepository;
import com.denunciayabackend.complaintCreation.interfaces.ComplaintEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
public class ComplaintCommandServiceImpl implements ComplaintCommandService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintEventPublisher eventPublisher;

    public ComplaintCommandServiceImpl(ComplaintRepository complaintRepository, ComplaintEventPublisher eventPublisher) {
        this.complaintRepository = complaintRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Complaint handle(CreateComplaintCommand command) {
        // Validaciones de negocio
        validateCreateCommand(command);

        Complaint complaint = new Complaint(command);
        Complaint savedComplaint = complaintRepository.save(complaint);

        // Publicar evento
        eventPublisher.publishComplaintCreatedEvent(new ComplaintCreatedEvent(savedComplaint));

        return savedComplaint;
    }

    @Override
    public Complaint handle(UpdateComplaintCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        // Validar que el complaint puede ser actualizado
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

        // Usar un valor por defecto si responsibleId no está disponible
        String responsibleId = command.assignedTo(); // o algún valor por defecto
        complaint.assignTo(command.assignedTo(), responsibleId);
        Complaint assignedComplaint = complaintRepository.save(complaint);

        eventPublisher.publishComplaintAssignedEvent(new ComplaintAssignedEvent(assignedComplaint));

        return assignedComplaint;
    }

    @Override
    public void handle(DeleteComplaintCommand command) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(command.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(command.complaintId()));

        // Validar que el complaint puede ser eliminado
        validateComplaintForDeletion(complaint);

        complaint.deleteComplaint();
        complaintRepository.save(complaint);

        eventPublisher.publishComplaintDeletedEvent(new ComplaintDeletedEvent(complaint));
    }

    @Override
    public void addEvidence(String complaintId, String evidenceUrl) {
        Complaint complaint = complaintRepository.findByComplaintIdValue(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));

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

    private void validateComplaintForDeletion(Complaint complaint) {
        // Permitir eliminar complaints rechazados también
        if (complaint.getStatus() == ComplaintStatus.COMPLETED) {
            throw new InvalidComplaintStatusException("Cannot delete a completed complaint");
        }
        // Ciudadanos solo pueden eliminar complaints en estado PENDING o UNDER_REVIEW
        if (!complaint.isCitizenDeletable()) {
            throw new InvalidComplaintStatusException(
                    "Only PENDING or UNDER_REVIEW complaints can be deleted by citizens. Current status: " + complaint.getStatus()
            );
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