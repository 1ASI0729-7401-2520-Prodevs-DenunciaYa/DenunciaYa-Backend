package com.denunciayabackend.authoritiesPanel.domain.model.queries;

public record GetResponsiblesByAccessLevelQuery(
        String accessLevel
) {
    public GetResponsiblesByAccessLevelQuery {
        if (accessLevel == null || accessLevel.isBlank()) {
            throw new IllegalArgumentException("Access level cannot be null or blank");
        }
    }
}