package com.denunciayabackend.complaintCreation.domain.model.queries;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;

public record GetComplaintsByStatusQuery(
        ComplaintStatus status
) {
    public ComplaintStatus status() {
        return this.status;
    }
}