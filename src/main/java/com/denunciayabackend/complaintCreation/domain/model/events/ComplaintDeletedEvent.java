package com.denunciayabackend.complaintCreation.domain.model.events;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;

public class ComplaintDeletedEvent {
    private final Complaint complaint;
    private final String eventType = "COMPLAINT_DELETED";
    private final Long timestamp;

    public ComplaintDeletedEvent(Complaint complaint) {
        this.complaint = complaint;
        this.timestamp = System.currentTimeMillis();
    }

    public String getComplaintId() {
        return complaint.getComplaintId();
    }


    public Complaint getComplaint() { return complaint; }
    public String getEventType() { return eventType; }
    public Long getTimestamp() { return timestamp; }
}