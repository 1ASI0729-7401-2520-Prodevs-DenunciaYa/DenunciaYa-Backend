package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ComplaintId(String value) {

    public ComplaintId {
        if (value == null) {
            throw new IllegalArgumentException("ComplaintId must be a positive number.");
        }
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ComplaintId cannot be null or empty.");
        }

        String normalized = value.trim();

        if (!normalized.matches("^\\d{6}$")) {
            throw new IllegalArgumentException("ComplaintId must be a 6-digit number.");
        }
    }
}
