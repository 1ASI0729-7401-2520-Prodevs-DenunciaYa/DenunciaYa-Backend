package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ResponsibleId;

public record UpdateResponsibleProfileCommand(
        Long responsibleId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {

    public UpdateResponsibleProfileCommand {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
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
    }

    public ResponsibleId getResponsibleIdVO() {
        return new ResponsibleId(responsibleId);
    }
}