package com.denunciayabackend.authoritiesPanel.domain.model.commands;

public record AssignComplaintCommand(
        String complaintId,
        String responsibleId,
        String assignedBy,
        String notes
) {
    public AssignComplaintCommand {
        if (complaintId == null || complaintId.isBlank()) {
            throw new IllegalArgumentException("Complaint ID cannot be null or blank");
        }
        if (responsibleId == null || responsibleId.isBlank()) {
            throw new IllegalArgumentException("Responsible ID cannot be null or blank");
        }
        if (assignedBy == null || assignedBy.isBlank()) {
            throw new IllegalArgumentException("Assigned by cannot be null or blank");
        }
    }
}