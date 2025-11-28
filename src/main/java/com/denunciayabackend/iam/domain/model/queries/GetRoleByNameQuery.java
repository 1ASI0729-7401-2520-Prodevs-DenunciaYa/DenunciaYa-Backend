package com.denunciayabackend.iam.domain.model.queries;


import com.denunciayabackend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}
