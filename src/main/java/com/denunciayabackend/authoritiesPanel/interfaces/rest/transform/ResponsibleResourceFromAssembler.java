package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleQueryService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ResponsibleResource;

public class ResponsibleResourceFromAssembler {

    public static ResponsibleResource fromResource(ResponsibleQueryService.ResponsibleService resource) {
        return new ResponsibleResource(
                resource.id(),
                resource.firstName(),
                resource.lastName(),
                resource.fullName(),
                resource.email(),
                resource.phone(),
                resource.role(),
                resource.description(),
                resource.accessLevel(),
                resource.position(),
                resource.department(),
                resource.statusResponsible()

        );
    }
}