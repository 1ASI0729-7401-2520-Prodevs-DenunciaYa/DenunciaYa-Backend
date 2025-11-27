package com.denunciayabackend.complaintCreation.interfaces;

import java.util.List;

public record CreateComplaintResource(
        String category,
        String department,
        String city,
        String district,
        String location,
        String referenceInfo,
        String description,
        String priority,
        List<String> evidence
) { }