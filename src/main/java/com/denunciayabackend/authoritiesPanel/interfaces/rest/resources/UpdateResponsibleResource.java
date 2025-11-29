package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record UpdateResponsibleResource(
        String firstName,
        String lastName,
        String email,
        String phone,
        String role,
        String description,
        String accessLevel,
        String status,
        String position,
        String department,
        String city,
        String district
) {
    public UpdateResponsibleResource {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or blank");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank");
        }
    }
}