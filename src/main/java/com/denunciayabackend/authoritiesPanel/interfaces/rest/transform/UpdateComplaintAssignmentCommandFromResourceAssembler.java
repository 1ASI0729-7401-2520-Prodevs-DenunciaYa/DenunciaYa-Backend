package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.UpdateAssignmentStatusCommand;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.UpdateComplaintAssignmentResource;

public class UpdateComplaintAssignmentCommandFromResourceAssembler {

    public static UpdateAssignmentStatusCommand toCommand(String assignmentId, UpdateComplaintAssignmentResource resource) {
        return new UpdateAssignmentStatusCommand(
                assignmentId,
                resource.status()
        );
    }
}