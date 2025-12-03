package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class MaximumAssignmentsExceededException extends AssignmentException {
    public MaximumAssignmentsExceededException(String responsibleId, long currentCount, long maxAllowed) {
        super(
                String.format("El responsable %s ya tiene %s asignaciones activas (máximo permitido: %s)",
                        responsibleId, currentCount, maxAllowed),
                "ASSIGNMENT_006",
                String.format("Responsible ID: %s, Current: %d, Max: %d",
                        responsibleId, currentCount, maxAllowed)
        );
    }
}