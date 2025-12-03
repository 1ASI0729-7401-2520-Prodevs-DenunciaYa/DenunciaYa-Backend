package com.denunciayabackend.complaintCreation.domain.model.events;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;

public class ComplaintAssignedEvent {
    private final Complaint complaint;
    private final String assignedTo;
    private final String responsibleId;
    private final String eventType = "COMPLAINT_ASSIGNED";
    private final Long timestamp;

    public ComplaintAssignedEvent(Complaint complaint) {
        this.complaint = complaint;
        this.assignedTo = complaint.getAssignedTo();
        this.responsibleId = complaint.getResponsibleId();
        this.timestamp = System.currentTimeMillis();
    }

    public String getComplaintId() {
        return complaint.getComplaintId();
    }

    public Complaint getComplaint() { return complaint; }
    public String getAssignedTo() { return assignedTo; }
    public String getResponsibleId() { return responsibleId; }
    public String getEventType() { return eventType; }
    public Long getTimestamp() { return timestamp; }
}