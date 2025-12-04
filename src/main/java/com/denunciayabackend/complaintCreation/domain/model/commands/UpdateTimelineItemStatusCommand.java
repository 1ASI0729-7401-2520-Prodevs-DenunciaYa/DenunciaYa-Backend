package com.denunciayabackend.complaintCreation.domain.model.commands;

public record UpdateTimelineItemStatusCommand(
        String complaintId,
        Long timelineItemId,
        Boolean completed,
        Boolean current,
        Boolean waitingDecision,
        String updateMessage
) { }