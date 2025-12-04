package com.denunciayabackend.complaintCreation.domain.model.commands;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;

import java.util.List;

public record UpdateComplaintCommand(
        String complaintId,
        String category,
        String department,
        String city,
        String district,
        String location,
        String referenceInfo,
        String description,
        ComplaintPriority priority,
        String updateMessage,
        String assignedTo,
        String responsibleId
) { }