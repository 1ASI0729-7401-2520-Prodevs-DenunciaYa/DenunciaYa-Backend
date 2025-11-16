package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(String firstName, String lastName) {

    public FullName {
        validate(firstName, "firstName");
        validate(lastName, "lastName");
    }

    private static void validate(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty or blank.");
        }

        int max = 100;
        if (trimmed.length() > max) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + max + " characters.");
        }

        // Letras (incluye acentuadas), espacios, puntos, apóstrofes y guiones
        String allowedRegex = "^[\\p{L} .'-]{1," + max + "}$";
        if (!trimmed.matches(allowedRegex)) {
            throw new IllegalArgumentException(fieldName + " contains invalid characters.");
        }
    }
}
