package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.events.*;

public interface ComplaintEventPublisher {
    void publishComplaintCreatedEvent(ComplaintCreatedEvent event);
    void publishComplaintUpdatedEvent(ComplaintUpdatedEvent event);
    void publishComplaintStatusUpdatedEvent(ComplaintStatusUpdatedEvent event);
    void publishComplaintAssignedEvent(ComplaintAssignedEvent event);
    void publishComplaintDeletedEvent(ComplaintDeletedEvent event);
}