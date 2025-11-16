package com.denunciayabackend.complaintCreation.domain.model.commands;

import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;

public record CreateComplaintCommand(
        String category,
        String departmentName,
        String provinceName,
        String districtName,
        String location,
        String referenceInfo,
        String description,
        ComplaintPriority priority
) { }