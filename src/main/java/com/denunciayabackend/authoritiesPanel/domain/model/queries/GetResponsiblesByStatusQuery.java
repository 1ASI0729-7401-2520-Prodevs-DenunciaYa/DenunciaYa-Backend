package com.denunciayabackend.authoritiesPanel.domain.model.queries;

public record GetResponsiblesByStatusQuery(
        String status
) {
    public GetResponsiblesByStatusQuery {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or blank");
        }
    }
}