package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class AssignmentNotFoundException extends AssignmentException {
    public AssignmentNotFoundException(String assignmentId) {
        super(
                String.format("No se encontró la asignación con ID: %s", assignmentId),
                "ASSIGNMENT_002",
                String.format("Assignment ID: %s", assignmentId)
        );
    }
}