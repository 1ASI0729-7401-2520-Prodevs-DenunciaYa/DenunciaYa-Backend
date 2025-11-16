package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ComplaintId(Long value) {

    public ComplaintId {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("ComplaintId must be a positive number.");
        }
    }
}
