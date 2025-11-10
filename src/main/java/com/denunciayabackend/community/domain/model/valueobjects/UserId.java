package com.denunciayabackend.community.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long userId) {
public UserId{
    if (userId == null || userId < 0) {
        throw new IllegalArgumentException("UserId must be a positive non-null value.");
    }
}
}
