package com.denunciayabackend.profiles.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Resource for a profile.
 */
public record ProfileResource(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        Long userId
) {
}
