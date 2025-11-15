package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String phoneNumber) {

    private static final String PHONE_REGEX = "^\\+?[0-9 ]{6,20}$";

    public PhoneNumber {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be null or blank.");
        }

        String normalized = phoneNumber.trim();

        if (!normalized.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        phoneNumber = normalized;
    }
}
