package com.denunciayabackend.complaintCreation.interfaces;

public record UpdateComplaintResource(
        String category,
        String departmentName,
        String provinceName,
        String districtName,
        String location,
        String referenceInfo,
        String description,
        String priority
) { }