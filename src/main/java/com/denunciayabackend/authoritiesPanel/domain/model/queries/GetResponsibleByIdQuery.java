package com.denunciayabackend.authoritiesPanel.domain.model.queries;

public record GetResponsibleByIdQuery(Long responsibleId) {
    public GetResponsibleByIdQuery {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
    }
}