package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class InvalidResponsibleException extends AssignmentException {
    public InvalidResponsibleException(String responsibleId) {
        super(
                String.format("El responsable %s no es válido o no existe", responsibleId),
                "ASSIGNMENT_004",
                String.format("Responsible ID: %s", responsibleId)
        );
    }
}