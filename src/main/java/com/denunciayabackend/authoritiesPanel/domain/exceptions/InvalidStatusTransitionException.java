package com.denunciayabackend.authoritiesPanel.domain.exceptions;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

public class InvalidStatusTransitionException extends AssignmentException {
    public InvalidStatusTransitionException(String assignmentId,
                                            AssignmentStatus from,
                                            AssignmentStatus to) {
        super(
                String.format("Transición de estado no permitida: de %s a %s para la asignación %s",
                        from, to, assignmentId),
                "ASSIGNMENT_005",
                String.format("Assignment ID: %s, From: %s, To: %s", assignmentId, from, to)
        );
    }
}