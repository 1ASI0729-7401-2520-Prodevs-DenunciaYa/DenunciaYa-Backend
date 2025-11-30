package com.denunciayabackend.authoritiesPanel.domain.services;


import com.denunciayabackend.authoritiesPanel.domain.model.commands.AssignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.CreateComplaintAssignmentCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.ReassignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.UpdateAssignmentStatusCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;

import java.util.List;

public interface ComplaintAssignmentService {

    /**
     * Assign a complaint to a responsible
     */
    String handle(AssignComplaintCommand command);

    /**
     * Reassign a complaint to a different responsible
     */
    void handle(ReassignComplaintCommand command);

    /**
     * Update the status of an assignment
     */
    void handle(UpdateAssignmentStatusCommand command);

    /**
     * Get active assignments for a responsible
     */
    List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId);

    /**
     * Get count of active complaints for a responsible
     */
    long getActiveComplaintsCountByResponsible(String responsibleId);

    /**
     * Get assignment history for a complaint
     */
    List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId);
    ComplaintAssignment getAssignmentById(Long assignmentId);

    String handle(CreateComplaintAssignmentCommand command);

    String handle(com.denunciayabackend.complaintCreation.domain.model.commands.AssignComplaintCommand command);
}