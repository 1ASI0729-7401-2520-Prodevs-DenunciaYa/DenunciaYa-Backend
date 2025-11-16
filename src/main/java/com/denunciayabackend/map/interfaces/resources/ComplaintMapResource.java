package com.denunciayabackend.map.interfaces.resources;

public record ComplaintMapResource(
        String id,
        String title,
        String category,
        String status,
        double latitude,
        double longitude
) {
}