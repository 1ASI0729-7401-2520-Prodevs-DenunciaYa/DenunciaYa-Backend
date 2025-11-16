package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record ResponsibleResource(
        Long id,
        String fullName,
        String email,
        String phone,
        String role,
        int assignedComplaintsCount
) { }
