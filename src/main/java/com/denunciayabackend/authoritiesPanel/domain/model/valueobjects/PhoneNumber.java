package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String value) {

    private static final String PHONE_REGEX = "^\\+?[0-9 ]{6,20}$";

    public PhoneNumber {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be null or blank.");
        }

        String normalized = value.trim();

        if (!normalized.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        value = normalized;
    }
}
