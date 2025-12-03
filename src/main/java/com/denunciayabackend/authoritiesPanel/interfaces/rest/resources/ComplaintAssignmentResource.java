package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ComplaintAssignmentResource(
        String id,
        String complaintId,
        String responsibleId,
        LocalDateTime assignedDate,
        String assignedBy,
        String status,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ComplaintAssignmentResource fromEntity(com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment entity) {
        return new ComplaintAssignmentResource(
                entity.getId(),
                entity.getComplaintId(),
                entity.getResponsibleId(),
                entity.getAssignedDate(),
                entity.getAssignedBy(),
                entity.getStatus().name(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}