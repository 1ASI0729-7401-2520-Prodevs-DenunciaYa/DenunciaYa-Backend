package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoOpComplaintEventPublisher implements ComplaintEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(NoOpComplaintEventPublisher.class);

    @Override
    public void publishComplaintCreatedEvent(ComplaintCreatedEvent event) {
        log.debug("NoOp publishComplaintCreatedEvent: {}", event);
    }

    @Override
    public void publishComplaintUpdatedEvent(ComplaintUpdatedEvent event) {
        log.debug("NoOp publishComplaintUpdatedEvent: {}", event);
    }

    @Override
    public void publishComplaintStatusUpdatedEvent(ComplaintStatusUpdatedEvent event) {
        log.debug("NoOp publishComplaintStatusUpdatedEvent: {}", event);
    }

    @Override
    public void publishComplaintAssignedEvent(ComplaintAssignedEvent event) {
        log.debug("NoOp publishComplaintAssignedEvent: {}", event);
    }

    @Override
    public void publishComplaintDeletedEvent(ComplaintDeletedEvent event) {
        log.debug("NoOp publishComplaintDeletedEvent: {}", event);
    }
}
