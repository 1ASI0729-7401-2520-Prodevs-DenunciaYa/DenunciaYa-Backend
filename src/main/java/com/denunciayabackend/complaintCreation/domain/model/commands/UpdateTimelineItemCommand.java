package com.denunciayabackend.complaintCreation.domain.model.commands;

public record UpdateTimelineItemCommand(
        String complaintId,
        Long timelineItemId,
        Boolean completed,
        Boolean current,
        Boolean waitingDecision,
        String status,
        String updateMessage
) { }
