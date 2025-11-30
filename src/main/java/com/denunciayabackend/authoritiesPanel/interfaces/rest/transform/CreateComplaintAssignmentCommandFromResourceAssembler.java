package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.CreateComplaintAssignmentResource;
import com.denunciayabackend.complaintCreation.domain.model.commands.AssignComplaintCommand;


public class CreateComplaintAssignmentCommandFromResourceAssembler {

    public static AssignComplaintCommand toCommand(CreateComplaintAssignmentResource resource) {
        return new AssignComplaintCommand(
                resource.complaintId(),
                resource.responsibleId(),
                resource.assignedBy()
        );
    }
}