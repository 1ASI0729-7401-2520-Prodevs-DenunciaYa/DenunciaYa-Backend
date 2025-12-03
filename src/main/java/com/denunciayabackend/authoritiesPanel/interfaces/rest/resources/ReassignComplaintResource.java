package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

public record ReassignComplaintResource(
        String newResponsibleId,
        String reassignedBy,
        String notes
) {
    public ReassignComplaintResource {
        if (newResponsibleId == null || newResponsibleId.isBlank()) {
            throw new IllegalArgumentException("New responsible ID cannot be null or blank");
        }
        if (reassignedBy == null || reassignedBy.isBlank()) {
            throw new IllegalArgumentException("Reassigned by cannot be null or blank");
        }
    }
}