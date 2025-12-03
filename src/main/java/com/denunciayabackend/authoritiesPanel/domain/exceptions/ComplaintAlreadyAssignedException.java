package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public class ComplaintAlreadyAssignedException extends AssignmentException {
    public ComplaintAlreadyAssignedException(String complaintId, String currentResponsibleId) {
        super(
                String.format("La denuncia %s ya está asignada al responsable %s", complaintId, currentResponsibleId),
                "ASSIGNMENT_001",
                String.format("Complaint ID: %s, Current Responsible: %s", complaintId, currentResponsibleId)
        );
    }
}