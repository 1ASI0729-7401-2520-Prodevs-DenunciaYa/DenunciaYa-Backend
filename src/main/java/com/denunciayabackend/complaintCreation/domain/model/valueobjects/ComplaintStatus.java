package com.denunciayabackend.complaintCreation.domain.model.valueobjects;

public enum ComplaintStatus {
    PENDING,           // "Pending" en JSON
    ACCEPTED,          // "Accepted" en JSON
    IN_PROCESS,        // "In Process" en JSON
    COMPLETED,         // "Completed" en JSON
    REJECTED,          // "Rejected" en JSON
    DRAFT,             // "Draft" en JSON
    AWAITING_RESPONSE; // "Awaiting response" en JSON

    // Método para convertir a formato JSON
    public String toJsonValue() {
        switch (this) {
            case PENDING: return "Pending";
            case ACCEPTED: return "Accepted";
            case IN_PROCESS: return "In Process";
            case COMPLETED: return "Completed";
            case REJECTED: return "Rejected";
            case DRAFT: return "Draft";
            case AWAITING_RESPONSE: return "Awaiting response";
            default: return this.name();
        }
    }

    // Método para convertir desde formato JSON
    public static ComplaintStatus fromJsonValue(String jsonValue) {
        if (jsonValue == null) return null;

        switch (jsonValue) {
            case "Pending": return PENDING;
            case "Accepted": return ACCEPTED;
            case "In Process": return IN_PROCESS;
            case "Completed": return COMPLETED;
            case "Rejected": return REJECTED;
            case "Draft": return DRAFT;
            case "Awaiting response": return AWAITING_RESPONSE;
            default: return valueOf(jsonValue.toUpperCase());
        }
    }
}