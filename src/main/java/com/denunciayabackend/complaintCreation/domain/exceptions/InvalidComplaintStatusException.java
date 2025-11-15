package com.denunciayabackend.complaintCreation.domain.exceptions;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;

public class InvalidComplaintStatusException extends RuntimeException {
    public InvalidComplaintStatusException(String message) {
        super(message);
    }

    public InvalidComplaintStatusException(String currentStatus, String attemptedAction) {
        super("Cannot perform action '" + attemptedAction + "' on complaint with status: " + currentStatus);
    }

    public InvalidComplaintStatusException(ComplaintStatus currentStatus, ComplaintStatus targetStatus) {
        super("Cannot transition complaint from status '" + currentStatus + "' to '" + targetStatus + "'");
    }
}