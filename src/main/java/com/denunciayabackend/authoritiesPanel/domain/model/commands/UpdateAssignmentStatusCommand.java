package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

public record UpdateAssignmentStatusCommand(
        String assignmentId,
        AssignmentStatus newStatus
) {
    public UpdateAssignmentStatusCommand {
        if (assignmentId == null || assignmentId.isBlank()) {
            throw new IllegalArgumentException("AssignmentId cannot be null or blank");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException("NewStatus cannot be null");
        }
    }
}