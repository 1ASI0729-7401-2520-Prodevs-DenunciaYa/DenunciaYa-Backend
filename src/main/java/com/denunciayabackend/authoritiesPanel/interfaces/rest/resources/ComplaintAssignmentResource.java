package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

import java.time.LocalDateTime;
import java.util.Date;

public record ComplaintAssignmentResource(
        Long id,
        String complaintId,
        String responsibleId,
        LocalDateTime assignedDate,
        String assignedBy,
        String status,
        Date createdAt,
        Date updatedAt
) {
    public static ComplaintAssignmentResource fromEntity(ComplaintAssignment assignment) {
        return new ComplaintAssignmentResource(
                assignment.getId(),
                assignment.getComplaintId(),
                assignment.getResponsibleId(),
                assignment.getAssignedDate(),
                assignment.getAssignedBy(),
                assignment.getStatus().name(),
                assignment.getCreatedAt(),
                 assignment.getUpdatedAt()
        );
    }
    private static LocalDateTime convertToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }
}