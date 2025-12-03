package com.denunciayabackend.authoritiesPanel.domain.services;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing complaint assignments.
 * Provides command handling and query operations related to the
 * lifecycle of complaint-to-responsible assignments.
 */
public interface ComplaintAssignmentService {

    /**
     * Handles the assignment of a complaint to a responsible user.
     */
    ComplaintAssignment handle(AssignComplaintCommand command);

    /**
     * Updates the status of an existing assignment.
     */
    ComplaintAssignment handle(UpdateAssignmentStatusCommand command);

    /**
     * Reassigns a complaint to a different responsible user.
     */
    ComplaintAssignment handle(ReassignComplaintCommand command);

    /**
     * Retrieves an assignment by its ID.
     */
    Optional<ComplaintAssignment> getAssignmentById(String id);

    /**
     * Retrieves the active assignment of a given complaint.
     */
    Optional<ComplaintAssignment> getActiveAssignmentByComplaintId(String complaintId);

    /**
     * Retrieves the full assignment history for a complaint.
     */
    List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId);

    /**
     * Retrieves active assignments associated with a responsible user.
     */
    List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId);

    /**
     * Retrieves all assignments (active or past) linked to a responsible user.
     */
    List<ComplaintAssignment> getAllAssignmentsByResponsible(String responsibleId);

    /**
     * Counts the number of currently active assignments of a responsible user.
     */
    long countActiveAssignmentsByResponsible(String responsibleId);

    /**
     * Checks whether a complaint already has an active assignment.
     */
    boolean isComplaintAlreadyAssigned(String complaintId);
}
