package com.denunciayabackend.complaintCreation.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

public record TimelineItemResource(
        @Schema(description = "Unique identifier of the timeline item", example = "1")
        Long id,

        @Schema(description = "Status of the timeline item", example = "Complaint updated")
        String status,

        @Schema(description = "Date of the timeline item", example = "2025-11-21T14:22:00")
        String date,

        @Schema(description = "Indicates if the timeline item is completed", example = "true")
        boolean completed,

        @Schema(description = "Indicates if the timeline item is the current one", example = "true")
        boolean current,

        @Schema(description = "Indicates if the timeline item is waiting for a decision", example = "false")
        boolean waitingDecision,

        @Schema(description = "Message associated with the timeline item", example = "Citizen updated complaint details")
        String updateMessage
) { }