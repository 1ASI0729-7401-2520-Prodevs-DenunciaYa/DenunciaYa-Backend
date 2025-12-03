package com.denunciayabackend.authoritiesPanel.domain.model.events;

public class AssignmentCancelledEvent extends AssignmentEvent {
    private final String complaintId;
    private final String responsibleId;
    private final String notes;

    public AssignmentCancelledEvent(String assignmentId, String complaintId,
                                    String responsibleId, String notes) {
        super(assignmentId);
        this.complaintId = complaintId;
        this.responsibleId = responsibleId;
        this.notes = notes;
    }

    public String getComplaintId() { return complaintId; }
    public String getResponsibleId() { return responsibleId; }
    public String getNotes() { return notes; }
}