package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

public record UpdateAssignmentStatusCommand(
        String assignmentId,
        AssignmentStatus status,
        String notes
) {
    public UpdateAssignmentStatusCommand {
        if (assignmentId == null || assignmentId.isBlank()) {
            throw new IllegalArgumentException("Assignment ID cannot be null or blank");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
}