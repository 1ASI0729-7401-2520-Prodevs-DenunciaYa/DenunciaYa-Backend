package com.denunciayabackend.authoritiesPanel.domain.model.commands;


public record ReassignComplaintCommand(
        String assignmentId,
        String newResponsibleId,
        String assignedBy
) {
    public ReassignComplaintCommand {
        if (assignmentId == null || assignmentId.isBlank()) {
            throw new IllegalArgumentException("AssignmentId cannot be null or blank");
        }
        if (newResponsibleId == null || newResponsibleId.isBlank()) {
            throw new IllegalArgumentException("NewResponsibleId cannot be null or blank");
        }
        if (assignedBy == null || assignedBy.isBlank()) {
            throw new IllegalArgumentException("AssignedBy cannot be null or blank");
        }
    }
}