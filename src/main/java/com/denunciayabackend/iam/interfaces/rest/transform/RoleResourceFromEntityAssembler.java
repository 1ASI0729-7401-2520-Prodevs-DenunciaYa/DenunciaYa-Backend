package com.denunciayabackend.iam.interfaces.rest.transform;

import com.denunciayabackend.iam.domain.model.entities.Role;
import com.denunciayabackend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
 public static RoleResource toResourceFromEntity(Role entity) {
    return new RoleResource(
            entity.getId(),
            entity.getStringName());
}
}
