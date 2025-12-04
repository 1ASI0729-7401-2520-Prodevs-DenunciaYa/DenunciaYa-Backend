package com.denunciayabackend.complaintCreation.domain.exceptions;

public class TimelineItemNotFoundException extends RuntimeException {
    public TimelineItemNotFoundException(Long id) {
        super("Timeline item not found: " + id);
    }
}
