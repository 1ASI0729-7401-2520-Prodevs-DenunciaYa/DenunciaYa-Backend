package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record CreateComplaintAssignmentResource(
        String complaintId,
        String responsibleId,
        String assignedBy
) {
    public CreateComplaintAssignmentResource {
        if (complaintId == null || complaintId.isBlank()) {
            throw new IllegalArgumentException("ComplaintId cannot be null or blank");
        }
        if (responsibleId == null || responsibleId.isBlank()) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or blank");
        }
        if (assignedBy == null || assignedBy.isBlank()) {
            throw new IllegalArgumentException("AssignedBy cannot be null or blank");
        }
    }
}