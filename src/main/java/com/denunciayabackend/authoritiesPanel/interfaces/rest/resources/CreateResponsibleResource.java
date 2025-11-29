package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resource for creating a new responsible")
public record CreateResponsibleResource(
        @Schema(description = "First name of the responsible", example = "Carlos")
        String firstName,

        @Schema(description = "Last name of the responsible", example = "Méndez")
        String lastName,

        @Schema(description = "Email address", example = "carlos.mendez@example.com")
        String email,

        @Schema(description = "Phone number", example = "+51 977 666 555")
        String phone,

        @Schema(description = "Role of the responsible", example = "Jefe")
        String role,

        @Schema(description = "Description of responsibilities", example = "Responsable del mantenimiento de espacios públicos")
        String description,

        @Schema(description = "Access level", example = "SUPERVISOR", allowableValues = {"SUPERVISOR", "ADMINISTRADOR", "TECNICO"})
        String accessLevel,

        @Schema(description = "Position in the organization", example = "Jefe de Mantenimiento")
        String position,

        @Schema(description = "Department", example = "Infrastructure and Public Spaces")
        String department
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
        if (position == null || position.isBlank()) {
            position = role;
        }
        if (department == null || department.isBlank()) {
            department = "Default Department";
        }
    }
}