package com.denunciayabackend.profiles.domain.model.commands;

import java.time.LocalDate;

/**
 * Create Profile Command
 */
public record CreateProfileCommand(String firstName,
                                   String lastName,
                                   String email,
                                   LocalDate birthDate,
                                   String phoneNumber,
                                   Long userId) {
}
