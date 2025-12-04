package com.denunciayabackend.complaintCreation.interfaces;

public record UpdateComplaintResource(
        String category,
        String department,
        String city,
        String district,
        String location,
        String referenceInfo,
        String description,
        String priority,
        java.util.List<String> evidence,
        String updateMessage,
        String assignedTo,
        String responsibleId
) { }