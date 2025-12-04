package com.denunciayabackend.complaintCreation.interfaces;

public record EvidenceResource(
        String id,
        String complaintId,
        String url,
        String uploadDate,
        String description,
        String fileName,
        String fileType,
        Long fileSize
) { }
