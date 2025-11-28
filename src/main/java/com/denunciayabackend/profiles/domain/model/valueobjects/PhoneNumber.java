package com.denunciayabackend.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String value) {
    public PhoneNumber() {
        this(null);
    }
}
