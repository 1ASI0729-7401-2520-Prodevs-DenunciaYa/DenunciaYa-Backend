package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String email) {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public Email {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }

        String normalized = email.trim().toLowerCase();

        if (!normalized.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        email = normalized;
    }
}
