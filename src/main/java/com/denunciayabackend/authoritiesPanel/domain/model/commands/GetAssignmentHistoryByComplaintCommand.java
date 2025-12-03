package com.denunciayabackend.authoritiesPanel.domain.model.commands;

public record GetAssignmentHistoryByComplaintCommand(
        String complaintId
) {
    public GetAssignmentHistoryByComplaintCommand {
        if (complaintId == null || complaintId.isBlank()) {
            throw new IllegalArgumentException("Complaint ID cannot be null or blank");
        }
    }
}