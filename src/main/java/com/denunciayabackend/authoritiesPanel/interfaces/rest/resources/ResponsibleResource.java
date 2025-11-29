package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record ResponsibleResource(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String phone,
        String role,
        String description,
        String accessLevel,
        String position,
        String department,
        String statusResponsible

) { }
