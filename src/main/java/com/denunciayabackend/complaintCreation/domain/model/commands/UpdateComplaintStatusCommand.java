package com.denunciayabackend.complaintCreation.domain.model.commands;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;

public record UpdateComplaintStatusCommand(
        String complaintId,
        ComplaintStatus newStatus,
        String updateMessage
) { }