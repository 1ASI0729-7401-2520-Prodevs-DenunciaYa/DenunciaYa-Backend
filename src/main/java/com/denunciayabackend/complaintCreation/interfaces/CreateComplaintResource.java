package com.denunciayabackend.complaintCreation.interfaces;

import java.util.List;

public record CreateComplaintResource(
        String category,
        String departmentName,
        String provinceName,
        String districtName,
        String location,
        String referenceInfo,
        String description,
        String priority,
        List<String> evidence
) { }