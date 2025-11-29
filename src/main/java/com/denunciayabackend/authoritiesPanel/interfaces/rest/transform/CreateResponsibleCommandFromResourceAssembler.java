package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.CreateResponsibleCommand;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.CreateResponsibleResource;

public class CreateResponsibleCommandFromResourceAssembler {

    public static CreateResponsibleCommand toCommand(CreateResponsibleResource resource) {
        return new CreateResponsibleCommand(

                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.role(),
                resource.description(),
                resource.accessLevel(),
                resource.role(),
                resource.position(),
                resource.department()


        );
    }
}