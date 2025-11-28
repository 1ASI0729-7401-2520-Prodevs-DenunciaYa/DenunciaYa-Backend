package com.denunciayabackend.profiles.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Resource for creating a profile.
 */
public record CreateProfileResource(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        String phoneNumber,
        Long userId
) {
    /**
     * Validates the resource.
     *
     * @throws IllegalArgumentException if the resource is invalid.
     */
    public CreateProfileResource {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (birthDate == null) throw new IllegalArgumentException("Birth date is required");
        if (phoneNumber == null || phoneNumber.isBlank()) throw new IllegalArgumentException("Phone number is required");
        if (userId == null) throw new IllegalArgumentException("User ID is required");
    }
}
