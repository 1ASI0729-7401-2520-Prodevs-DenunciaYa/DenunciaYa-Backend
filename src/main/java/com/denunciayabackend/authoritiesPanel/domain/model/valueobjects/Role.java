package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Role(String value) {

    public Role {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 50) {
            throw new IllegalArgumentException("Role cannot exceed 50 characters.");
        }

        if (!normalized.matches("^[\\p{L}0-9\\s\\-_,.()]+$")) {
            throw new IllegalArgumentException("Role contains invalid characters.");
        }
    }

    public String getValue() {
        return value;
    }
}