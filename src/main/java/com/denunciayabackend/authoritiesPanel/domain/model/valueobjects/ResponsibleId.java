package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ResponsibleId(String responsibleId) {
    public ResponsibleId {
        if (responsibleId == null || responsibleId.trim().isEmpty()) {
            throw new IllegalArgumentException("ResponsibleId " +
                    "cannot be null or less than 1.");
        }
    }
    public String getValue() {
        return responsibleId; // Now 'value' matches the record component
    }
}
