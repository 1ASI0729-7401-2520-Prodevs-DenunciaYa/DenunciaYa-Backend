package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String value) {

    public Email {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }

        String normalized = value.trim().toLowerCase();

        if (normalized.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters.");
        }

        // Validación básica de email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!normalized.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public String getValue() {
        return value;
    }
}