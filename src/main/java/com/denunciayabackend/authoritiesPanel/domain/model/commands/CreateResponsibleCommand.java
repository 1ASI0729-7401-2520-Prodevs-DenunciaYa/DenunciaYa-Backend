package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AccessLevel;

/**
 * Command to create a new Responsible.
 */
public record CreateResponsibleCommand(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String role,
        String description,
        String accessLevel,
        String position,
        String department
) {

    public CreateResponsibleCommand {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
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

        try {
            AccessLevel.valueOf(accessLevel.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Access level is invalid: " + accessLevel);
        }

        role = role.trim().toUpperCase();
        accessLevel = accessLevel.trim().toUpperCase();
    }
}
