package com.denunciayabackend.complaintCreation.domain.exceptions;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(String complaintId) {
        super("Complaint not found with id: " + complaintId);
    }

    public ComplaintNotFoundException(Long id) {
        super("Complaint not found with internal id: " + id);
    }
}