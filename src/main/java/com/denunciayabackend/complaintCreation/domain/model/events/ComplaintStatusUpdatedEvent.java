package com.denunciayabackend.complaintCreation.domain.model.events;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;

public class ComplaintStatusUpdatedEvent {
    private final Complaint complaint;
    private final ComplaintStatus previousStatus;
    private final ComplaintStatus newStatus;
    private final String updateMessage;
    private final String eventType = "COMPLAINT_STATUS_UPDATED";
    private final Long timestamp;

    public ComplaintStatusUpdatedEvent(Complaint complaint, ComplaintStatus previousStatus, String updateMessage) {
        this.complaint = complaint;
        this.previousStatus = previousStatus;
        this.newStatus = complaint.getStatus();
        this.updateMessage = updateMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public String getComplaintId() {
        return complaint.getComplaintId();
    }

    public Complaint getComplaint() { return complaint; }
    public ComplaintStatus getPreviousStatus() { return previousStatus; }
    public ComplaintStatus getNewStatus() { return newStatus; }
    public String getUpdateMessage() { return updateMessage; }
    public String getEventType() { return eventType; }
    public Long getTimestamp() { return timestamp; }
}