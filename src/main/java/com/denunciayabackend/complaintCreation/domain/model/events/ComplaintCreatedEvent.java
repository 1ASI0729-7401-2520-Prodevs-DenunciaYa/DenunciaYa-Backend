package com.denunciayabackend.complaintCreation.domain.model.events;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;

public class ComplaintCreatedEvent {
    private final Complaint complaint;
    private final String eventType = "COMPLAINT_CREATED";
    private final Long timestamp;

    public ComplaintCreatedEvent(Complaint complaint) {
        this.complaint = complaint;
        this.timestamp = System.currentTimeMillis();
    }

    public String getComplaintId() {
        return complaint.getComplaintId(); // Cambiado a getComplaintId()
    }

    public String getCategory() {
        return complaint.getCategory();
    }

    // ELIMINADO: getUserId() ya que Complaint no tiene userId

    public Complaint getComplaint() { return complaint; }
    public String getEventType() { return eventType; }
    public Long getTimestamp() { return timestamp; }
}