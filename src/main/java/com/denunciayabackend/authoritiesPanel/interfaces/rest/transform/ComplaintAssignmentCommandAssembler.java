package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.AssignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.CreateComplaintAssignmentResource;

public class ComplaintAssignmentCommandAssembler {

    public static AssignComplaintCommand toAssignCommandFromResource(CreateComplaintAssignmentResource resource) {
        return new AssignComplaintCommand(
                resource.complaintId(),
                resource.responsibleId(),
                resource.assignedBy(),
                resource.notes()
        );
    }
}