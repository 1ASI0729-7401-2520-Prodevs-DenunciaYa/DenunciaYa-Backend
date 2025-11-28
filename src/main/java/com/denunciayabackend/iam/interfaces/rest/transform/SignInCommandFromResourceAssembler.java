package com.denunciayabackend.iam.interfaces.rest.transform;

import com.denunciayabackend.iam.domain.model.commands.SignInCommand;
import com.denunciayabackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.username(),
                resource.password());
    }
}
