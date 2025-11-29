package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.UpdateResponsibleProfileCommand;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.UpdateResponsibleResource;

public class UpdateResponsibleCommandFromResourceAssembler {

    public static UpdateResponsibleProfileCommand toCommand(String id, UpdateResponsibleResource resource) {
        return new UpdateResponsibleProfileCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.role(),
                resource.description(),
                resource.accessLevel(),
                resource.status(),
                resource.position(),
                resource.department(),
                resource.city(),
                resource.district()
        );
    }
}