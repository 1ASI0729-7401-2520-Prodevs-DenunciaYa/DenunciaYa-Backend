package com.denunciayabackend.map.interfaces.resources;

public record ComplaintMapResource(
        String id,
        String title,
        String category,
        String district,
        String status,
        double latitude,
        double longitude
) {
}