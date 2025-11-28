package com.denunciayabackend.iam.domain.services;


import com.denunciayabackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {

    void handle(SeedRolesCommand command);


}
