package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

public record UpdateComplaintAssignmentResource(
        AssignmentStatus status
) {
    public UpdateComplaintAssignmentResource {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
}