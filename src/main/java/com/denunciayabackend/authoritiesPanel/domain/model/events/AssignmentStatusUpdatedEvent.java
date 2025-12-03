package com.denunciayabackend.authoritiesPanel.domain.model.events;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;

public class AssignmentStatusUpdatedEvent extends AssignmentEvent {
    private final AssignmentStatus oldStatus;
    private final AssignmentStatus newStatus;
    private final String notes;

    public AssignmentStatusUpdatedEvent(String assignmentId, AssignmentStatus oldStatus,
                                        AssignmentStatus newStatus, String notes) {
        super(assignmentId);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.notes = notes;
    }

    public AssignmentStatus getOldStatus() { return oldStatus; }
    public AssignmentStatus getNewStatus() { return newStatus; }
    public String getNotes() { return notes; }
}