package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class AssignmentValidationException extends AssignmentException {
    public AssignmentValidationException(String field, String reason) {
        super(
                String.format("Validación fallida para el campo %s: %s", field, reason),
                "ASSIGNMENT_007",
                String.format("Field: %s, Reason: %s", field, reason)
        );
    }
}