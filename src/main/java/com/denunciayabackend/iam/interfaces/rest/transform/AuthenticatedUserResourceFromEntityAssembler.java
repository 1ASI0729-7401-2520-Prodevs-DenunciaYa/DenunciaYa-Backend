package com.denunciayabackend.iam.interfaces.rest.transform;

import com.denunciayabackend.iam.domain.model.aggregates.User;
import com.denunciayabackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
  
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String token) {
        return new AuthenticatedUserResource(
                entity.getId(),
                entity.getUsername(),
                token);
    }
}