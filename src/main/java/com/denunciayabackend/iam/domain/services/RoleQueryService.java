package com.denunciayabackend.iam.domain.services;


import com.denunciayabackend.iam.domain.model.entities.Role;
import com.denunciayabackend.iam.domain.model.queries.GetAllRolesQuery;
import com.denunciayabackend.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;
public interface RoleQueryService {
    /**
     * Handle get all roles query.
     * <p>
     *     Get all roles query is used to get all roles in the system.
     * </p>
     *
     * @param query the {@link GetAllRolesQuery} query
     * @return the list of {@link Role} roles
     */
    List<Role> handle(GetAllRolesQuery query);


    Optional<Role> handle(GetRoleByNameQuery query);
}

