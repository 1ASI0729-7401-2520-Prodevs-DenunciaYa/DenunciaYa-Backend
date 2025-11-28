package com.denunciayabackend.iam.interfaces.rest.transform;


import com.denunciayabackend.iam.domain.model.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleListFromStringAssembler {
    public static List<Role> toRoleListFromStringList(List<String> resourceList) {
        return Objects.nonNull(resourceList) ? resourceList.stream()
                .map(Role::toRoleFromName).toList() : new ArrayList<Role>();
    }
}
