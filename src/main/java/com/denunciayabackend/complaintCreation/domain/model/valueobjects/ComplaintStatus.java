package com.denunciayabackend.complaintCreation.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComplaintStatus {
    PENDING,
    IN_PROCESS,
    COMPLETED,
    REJECTED,
    AWAITING_RESPONSE;

    @JsonValue
    public String toJsonValue() {
        switch (this) {
            case PENDING: return "Pending";
            case IN_PROCESS: return "In Process";
            case COMPLETED: return "Completed";
            case REJECTED: return "Rejected";
            case AWAITING_RESPONSE: return "Awaiting Response";
            default: return this.name();
        }
    }

    @JsonCreator
    public static ComplaintStatus fromJsonValue(String jsonValue) {
        if (jsonValue == null) return null;

        switch (jsonValue) {
            case "Pending": return PENDING;
            case "In Process": return IN_PROCESS;
            case "Completed": return COMPLETED;
            case "Rejected": return REJECTED;
            case "Awaiting Response": return AWAITING_RESPONSE;
            default: return valueOf(jsonValue.toUpperCase().replace(" ", "_"));
        }
    }
}