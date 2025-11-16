package com.denunciayabackend.map.domain.model.queries;

public record GetComplaintsForMapQuery(
        String category,
        String district,
        String status
) {
}