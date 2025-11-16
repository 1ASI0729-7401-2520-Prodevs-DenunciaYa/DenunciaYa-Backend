package com.denunciayabackend.complaintCreation.domain.exceptions;

public class ComplaintValidationException extends RuntimeException {
    public ComplaintValidationException(String message) {
        super(message);
    }

    public ComplaintValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComplaintValidationException(String field, String value, String constraint) {
        super(String.format("Validation failed for field '%s' with value '%s': %s", field, value, constraint));
    }
}