package com.denunciayabackend.complaintCreation.domain.model.valueobjects;

public enum ComplaintPriority {
    STANDARD,
    URGENT,
    CRITICAL;

    public String toJsonValue() {
        switch (this) {
            case STANDARD: return "Standard";
            case URGENT: return "Urgent";
            case CRITICAL: return "Critical";
            default: return this.name();
        }
    }

    public static ComplaintPriority fromJsonValue(String jsonValue) {
        if (jsonValue == null) return null;

        switch (jsonValue) {
            case "Standard": return STANDARD;
            case "Urgent": return URGENT;
            case "Critical": return CRITICAL;
            default: return valueOf(jsonValue.toUpperCase());
        }
    }
}