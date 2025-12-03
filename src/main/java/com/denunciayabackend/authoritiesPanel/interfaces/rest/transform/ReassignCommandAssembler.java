package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.ReassignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ReassignComplaintResource;

public class ReassignCommandAssembler {

    public static ReassignComplaintCommand toCommandFromResource(String assignmentId,
                                                                 ReassignComplaintResource resource) {
        return new ReassignComplaintCommand(
                assignmentId,
                resource.newResponsibleId(),
                resource.reassignedBy(),
                resource.notes()
        );
    }
}