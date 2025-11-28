package com.denunciayabackend.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstName, String lastName) {
    public PersonName() {
        this(null, null);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
