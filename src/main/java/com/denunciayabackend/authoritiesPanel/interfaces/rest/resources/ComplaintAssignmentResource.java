package com.denunciayabackend.authoritiesPanel.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ComplaintAssignmentResource(
        Long complaintId,
        LocalDateTime assignedAt,
        String status
) { }