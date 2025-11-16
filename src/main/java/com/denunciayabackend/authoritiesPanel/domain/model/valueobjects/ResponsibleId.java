package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ResponsibleId(Long responsibleId) {
    public ResponsibleId {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId " +
                    "cannot be null or less than 1.");
        }
    }
}
