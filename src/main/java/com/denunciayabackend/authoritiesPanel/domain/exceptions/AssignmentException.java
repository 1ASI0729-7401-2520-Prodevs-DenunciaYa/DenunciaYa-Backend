package com.denunciayabackend.authoritiesPanel.domain.exceptions;

public abstract class AssignmentException extends RuntimeException {
    private final String errorCode;
    private final String details;

    protected AssignmentException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    protected AssignmentException(String message, String errorCode, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() { return errorCode; }
    public String getDetails() { return details; }
}