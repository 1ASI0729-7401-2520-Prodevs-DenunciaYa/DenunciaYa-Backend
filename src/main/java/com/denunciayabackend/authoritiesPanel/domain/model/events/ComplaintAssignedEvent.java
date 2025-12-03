package com.denunciayabackend.authoritiesPanel.domain.model.events;

public class ComplaintAssignedEvent extends AssignmentEvent {
    private final String complaintId;
    private final String responsibleId;
    private final String assignedBy;
    private final String notes;

    public ComplaintAssignedEvent(String assignmentId, String complaintId,
                                  String responsibleId, String assignedBy, String notes) {
        super(assignmentId);
        this.complaintId = complaintId;
        this.responsibleId = responsibleId;
        this.assignedBy = assignedBy;
        this.notes = notes;
    }

    public String getComplaintId() { return complaintId; }
    public String getResponsibleId() { return responsibleId; }
    public String getAssignedBy() { return assignedBy; }
    public String getNotes() { return notes; }
}