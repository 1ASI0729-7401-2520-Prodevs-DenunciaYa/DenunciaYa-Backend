package com.denunciayabackend.complaintCreation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ComplaintId(String value) {
    public ComplaintId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Complaint ID cannot be null or blank");
        }
        if (!value.matches("\\d{6}")) {
            throw new IllegalArgumentException("Complaint ID must be a 6-digit number");
        }
    }

    public String getValue() {
        return value;
    }

    public String value() {
        return value;
    }
}