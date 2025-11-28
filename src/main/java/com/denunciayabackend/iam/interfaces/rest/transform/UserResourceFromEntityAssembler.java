package com.denunciayabackend.iam.interfaces.rest.transform;

import com.denunciayabackend.iam.domain.model.aggregates.User;
import com.denunciayabackend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
                entity.getId(),
                entity.getUsername(),
                RoleStringListFromEntityListAssembler.toResourceListFromEntitySet(entity.getRoles()));
    }
}
