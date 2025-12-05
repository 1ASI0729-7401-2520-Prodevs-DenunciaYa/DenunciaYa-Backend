package com.denunciayabackend.authoritiesPanel.application.internal.commanservices;

import com.denunciayabackend.authoritiesPanel.domain.exceptions.*;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.events.AssignmentEvent;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import com.denunciayabackend.authoritiesPanel.domain.services.ComplaintAssignmentService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ComplaintAssignmentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ComplaintAssignmentServiceImpl
 * Service implementation for managing complaint assignments.
 * Handles assignment creation, status updates, reassignment, completion, cancellation,
 * and retrieval operations. Publishes domain events triggered by state changes.
 */
@Service
public class ComplaintAssignmentServiceImpl implements ComplaintAssignmentService {

    private final ComplaintAssignmentRepository complaintAssignmentRepository;
    private final ApplicationEventPublisher eventPublisher;
    /**
     * Maximum number of active assignments that a responsible can have.
     */
    private static final long MAX_ASSIGNMENTS_PER_RESPONSIBLE = 50;

    public ComplaintAssignmentServiceImpl(ComplaintAssignmentRepository complaintAssignmentRepository,
                                          @Qualifier("applicationEventPublisher") ApplicationEventPublisher eventPublisher) {
        this.complaintAssignmentRepository = complaintAssignmentRepository;
        this.eventPublisher = eventPublisher;
    }
    /**
     * Creates a new complaint assignment.
     *
     * @param command the command containing complaint ID, responsible ID, assignedBy and notes
     * @return the created complaint assignment
     * @throws ComplaintAlreadyAssignedException if the complaint already has an active assignment
     * @throws MaximumAssignmentsExceededException if the responsible has reached the assignment limit
     */
    @Override
    public ComplaintAssignment handle(AssignComplaintCommand command) {
        if (isComplaintAlreadyAssigned(command.complaintId())) {
            Optional<ComplaintAssignment> existingAssignment =
                    complaintAssignmentRepository.findByComplaintIdAndStatus(command.complaintId(), AssignmentStatus.ACTIVE);
            String currentResponsibleId = existingAssignment.map(ComplaintAssignment::getResponsibleId).orElse("Desconocido");
            throw new ComplaintAlreadyAssignedException(command.complaintId(), currentResponsibleId);
        }

        long currentAssignments = countActiveAssignmentsByResponsible(command.responsibleId());
        if (currentAssignments >= MAX_ASSIGNMENTS_PER_RESPONSIBLE) {
            throw new MaximumAssignmentsExceededException(
                    command.responsibleId(),
                    currentAssignments,
                    MAX_ASSIGNMENTS_PER_RESPONSIBLE
            );
        }

        ComplaintAssignment assignment = ComplaintAssignment.create(
                command.complaintId(),
                command.responsibleId(),
                command.assignedBy(),
                command.notes()
        );

        ComplaintAssignment savedAssignment = complaintAssignmentRepository.save(assignment);

        publishDomainEvents(savedAssignment);

        return savedAssignment;
    }
    /**
     * Updates the status of an existing assignment.
     *
     * @param command the command containing assignment ID, new status and notes
     * @return the updated complaint assignment
     * @throws AssignmentNotFoundException if the assignment does not exist
     */
    @Override
    public ComplaintAssignment handle(UpdateAssignmentStatusCommand command) {
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new AssignmentNotFoundException(command.assignmentId()));

        assignment.updateStatus(command.status(), command.notes());

        ComplaintAssignment updatedAssignment = complaintAssignmentRepository.save(assignment);

        publishDomainEvents(updatedAssignment);

        return updatedAssignment;
    }
    /**
     * Reassigns an existing complaint assignment to a new responsible.
     *
     * @param command the command containing assignment ID, new responsible ID, reassignedBy and notes
     * @return the reassigned complaint assignment
     * @throws AssignmentNotFoundException if the assignment does not exist
     * @throws MaximumAssignmentsExceededException if the new responsible has reached the assignment limit
     */
    @Override
    public ComplaintAssignment handle(ReassignComplaintCommand command) {
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new AssignmentNotFoundException(command.assignmentId()));

        long currentAssignments = countActiveAssignmentsByResponsible(command.newResponsibleId());
        if (currentAssignments >= MAX_ASSIGNMENTS_PER_RESPONSIBLE) {
            throw new MaximumAssignmentsExceededException(
                    command.newResponsibleId(),
                    currentAssignments,
                    MAX_ASSIGNMENTS_PER_RESPONSIBLE
            );
        }

        assignment.reassign(command.newResponsibleId(), command.reassignedBy(), command.notes());

        ComplaintAssignment reassignedAssignment = complaintAssignmentRepository.save(assignment);

        publishDomainEvents(reassignedAssignment);

        return reassignedAssignment;
    }
    /**
     * Retrieves an assignment by its ID.
     *
     * @param id the assignment ID
     * @return an Optional containing the assignment if found, otherwise empty
     */
    @Override
    public Optional<ComplaintAssignment> getAssignmentById(String id) {
        return complaintAssignmentRepository.findById(id);
    }
    /**
     * Retrieves the active assignment linked to a specific complaint.
     *
     * @param complaintId the complaint ID
     * @return an Optional containing the active assignment, if present
     */
    @Override
    public Optional<ComplaintAssignment> getActiveAssignmentByComplaintId(String complaintId) {
        return complaintAssignmentRepository.findByComplaintIdAndStatus(complaintId, AssignmentStatus.ACTIVE);
    }
    /**
     * Retrieves all assignment records for a complaint, ordered by assignment date descending.
     *
     * @param complaintId the complaint ID
     * @return a list of assignments associated with the complaint
     */
    @Override
    public List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId) {
        return complaintAssignmentRepository.findByComplaintIdOrderByAssignedDateDesc(complaintId);
    }
    /**
     * Retrieves all active assignments for a responsible.
     *
     * @param responsibleId the responsible's ID
     * @return list of active assignments
     */
    @Override
    public List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.findByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }
    /**
     * Retrieves all assignments for a responsible, including completed or cancelled ones.
     *
     * @param responsibleId the responsible's ID
     * @return list of all assignments ordered by assigned date
     */
    @Override
    public List<ComplaintAssignment> getAllAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.findByResponsibleIdOrderByAssignedDateDesc(responsibleId);
    }
    /**
     * Counts the active assignments assigned to a responsible.
     *
     * @param responsibleId the responsible's ID
     * @return number of active assignments
     */
    @Override
    public long countActiveAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.countByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }
    /**
     * Checks if a complaint already has an active assignment.
     *
     * @param complaintId the complaint ID
     * @return true if the complaint is already assigned, false otherwise
     */
    @Override
    public boolean isComplaintAlreadyAssigned(String complaintId) {
        return complaintAssignmentRepository.existsActiveAssignmentForComplaint(complaintId);
    }

    /**
     * Completes an assignment.
     *
     * @param assignmentId the assignment ID
     * @param notes additional notes related to the completion
     * @return the completed assignment
     * @throws AssignmentNotFoundException if the assignment does not exist
     */
    public ComplaintAssignment completeAssignment(String assignmentId, String notes) {
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        assignment.completeAssignment(notes);

        ComplaintAssignment completedAssignment = complaintAssignmentRepository.save(assignment);
        publishDomainEvents(completedAssignment);

        return completedAssignment;
    }
    /**
     * Cancels an assignment.
     *
     * @param assignmentId the assignment ID
     * @param notes additional notes explaining the reason for cancellation
     * @return the cancelled assignment
     * @throws AssignmentNotFoundException if the assignment does not exist
     */
    public ComplaintAssignment cancelAssignment(String assignmentId, String notes) {
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        assignment.cancelAssignment(notes);

        ComplaintAssignment cancelledAssignment = complaintAssignmentRepository.save(assignment);
        publishDomainEvents(cancelledAssignment);

        return cancelledAssignment;
    }
    /**
     * Reopens a previously closed assignment.
     *
     * @param assignmentId the assignment ID
     * @param notes additional notes explaining the reopening
     * @return the reopened assignment
     * @throws AssignmentNotFoundException if the assignment does not exist
     */
    public ComplaintAssignment reopenAssignment(String assignmentId, String notes) {
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        assignment.reopenAssignment(notes);

        ComplaintAssignment reopenedAssignment = complaintAssignmentRepository.save(assignment);
        publishDomainEvents(reopenedAssignment);

        return reopenedAssignment;
    }
    /**
     * Publishes all domain events associated with the given assignment.
     * Events are cleared after dispatching.
     *
     * @param assignment the assignment whose domain events should be published
     */
    private void publishDomainEvents(ComplaintAssignment assignment) {
        for (AssignmentEvent event : assignment.getDomainEvents()) {
            eventPublisher.publishEvent(event);
        }
        assignment.clearDomainEvents();
    }
}