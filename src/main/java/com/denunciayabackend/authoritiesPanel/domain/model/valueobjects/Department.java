package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Department(String value) {

    public Department {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new IllegalArgumentException("Department cannot exceed 80 characters.");
        }

        value = normalized;
    }
    public String getValue() {
        return value;
    }
}
