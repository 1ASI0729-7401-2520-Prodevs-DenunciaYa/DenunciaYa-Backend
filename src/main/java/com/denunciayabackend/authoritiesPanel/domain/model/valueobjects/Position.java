package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Position(String value) {

    public Position {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 60) {
            throw new IllegalArgumentException("Position cannot exceed 60 characters.");
        }

        value = normalized;
    }
}
