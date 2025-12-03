package com.denunciayabackend.authoritiesPanel.domain.model.events;

public class ComplaintReassignedEvent extends AssignmentEvent {
    private final String oldResponsibleId;
    private final String newResponsibleId;
    private final String reassignedBy;
    private final String notes;

    public ComplaintReassignedEvent(String assignmentId, String oldResponsibleId,
                                    String newResponsibleId, String reassignedBy, String notes) {
        super(assignmentId);
        this.oldResponsibleId = oldResponsibleId;
        this.newResponsibleId = newResponsibleId;
        this.reassignedBy = reassignedBy;
        this.notes = notes;
    }

    public String getOldResponsibleId() { return oldResponsibleId; }
    public String getNewResponsibleId() { return newResponsibleId; }
    public String getReassignedBy() { return reassignedBy; }
    public String getNotes() { return notes; }
}