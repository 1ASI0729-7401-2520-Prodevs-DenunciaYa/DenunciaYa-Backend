package com.denunciayabackend.authoritiesPanel.domain.model.queries;

public record SearchResponsibleQuery(String keyword) {

    public SearchResponsibleQuery {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be null or blank.");
        }
    }
}
