package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class InvalidAssignmentStatusException extends AssignmentException {
    public InvalidAssignmentStatusException(String assignmentId, String currentStatus, String requiredStatus) {
        super(
                String.format("La asignación %s tiene estado %s, pero se requiere %s",
                        assignmentId, currentStatus, requiredStatus),
                "ASSIGNMENT_003",
                String.format("Assignment ID: %s, Current: %s, Required: %s",
                        assignmentId, currentStatus, requiredStatus)
        );
    }
}