package com.denunciayabackend.complaintCreation.domain.exceptions;

public class ComplaintAlreadyExistsException extends RuntimeException {
    public ComplaintAlreadyExistsException(String complaintId) {
        super("Complaint already exists with id: " + complaintId);
    }

    public ComplaintAlreadyExistsException(String complaintId, String message) {
        super("Complaint already exists with id: " + complaintId + ". " + message);
    }
}