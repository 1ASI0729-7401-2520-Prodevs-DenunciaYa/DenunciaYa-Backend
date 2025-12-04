package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;

import java.util.List;

public record ComplaintResource(
        String id,
        String category,
        String department,
        String city,
        String district,
        String location,
        String referenceInfo,
        String description,
        ComplaintStatus status,
        com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority priority,
        List<String> evidence,
        List<EvidenceResource> evidences,
        String assignedTo,
        String responsibleId,
        String updateMessage,
        String updateDate,
        List<TimelineItemResource> timeline
) { }

