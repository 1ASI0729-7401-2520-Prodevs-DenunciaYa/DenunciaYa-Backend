package com.denunciayabackend.complaintCreation.domain.model.commands;

public record AssignComplaintCommand(
        String complaintId,
        String assignedTo,
        String responsibleId
) { }