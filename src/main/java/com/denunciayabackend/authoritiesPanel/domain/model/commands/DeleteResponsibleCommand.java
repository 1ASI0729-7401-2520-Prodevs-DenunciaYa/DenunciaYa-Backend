package com.denunciayabackend.authoritiesPanel.domain.model.commands;

public record DeleteResponsibleCommand(Long responsibleId) {
    public DeleteResponsibleCommand {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
    }
}