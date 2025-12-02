package com.denunciayabackend.authoritiesPanel.domain.model.queries;

public record GetResponsibleByIdQuery(Long responsibleId) {
    public GetResponsibleByIdQuery {
        // CORRECCIÓN AQUÍ: Debe ser menor a 1 (o menor o igual a 0)
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
    }
}