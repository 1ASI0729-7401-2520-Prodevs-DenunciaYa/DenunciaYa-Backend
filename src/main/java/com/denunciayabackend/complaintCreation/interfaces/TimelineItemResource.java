package com.denunciayabackend.complaintCreation.interfaces;

public record TimelineItemResource(
        Long id,
        String status,
        String date,
        boolean completed,
        boolean current,
        boolean waitingDecision,
        String updateMessage
) { }