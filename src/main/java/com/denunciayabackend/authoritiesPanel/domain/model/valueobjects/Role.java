package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Role(String role) {

    public Role {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or blank.");
        }

        String normalized = role.trim().toUpperCase();

        if (normalized.length() > 50) {
            throw new IllegalArgumentException("Role cannot exceed 50 characters.");
        }

        if (!normalized.matches("^[A-Z0-9 _-]+$")) {
            throw new IllegalArgumentException("Role contains invalid characters.");
        }

        role = normalized;
    }
}
