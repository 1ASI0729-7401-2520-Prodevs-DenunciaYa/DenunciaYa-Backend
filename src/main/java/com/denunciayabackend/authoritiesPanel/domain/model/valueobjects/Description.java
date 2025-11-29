package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String value) {

    public Description {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 50) {
            throw new IllegalArgumentException("Description cannot exceed 50 characters.");
        }

        value = normalized;
    }
    public String getValue() {
        return value;
    }
}
