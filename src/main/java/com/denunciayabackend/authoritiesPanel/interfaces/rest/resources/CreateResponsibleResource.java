package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record CreateResponsibleResource(
        String firstName,
        String lastName,
        String email,
        String phone,
        String role,
        String description,
        String accessLevel
) {
    public CreateResponsibleResource {
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
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (accessLevel == null || accessLevel.isBlank()) {
            throw new IllegalArgumentException("Access level cannot be null or blank");
        }
    }
}
