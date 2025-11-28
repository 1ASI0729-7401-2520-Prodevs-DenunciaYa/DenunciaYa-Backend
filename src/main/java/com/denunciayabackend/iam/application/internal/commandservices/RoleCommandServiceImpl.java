package com.denunciayabackend.iam.application.internal.commandservices;


import com.denunciayabackend.iam.domain.model.commands.SeedRolesCommand;
import com.denunciayabackend.iam.domain.model.entities.Role;
import com.denunciayabackend.iam.domain.model.valueobjects.Roles;
import com.denunciayabackend.iam.domain.services.RoleCommandService;
import com.denunciayabackend.iam.infraestructure.persistence.jpa.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // inherit javadoc
    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
