package com.denunciayabackend.profiles.application.internal.commandservices;


import com.denunciayabackend.profiles.domain.model.aggregates.Profile;
import com.denunciayabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.denunciayabackend.profiles.domain.model.valueobjects.EmailAddress;
import com.denunciayabackend.profiles.domain.services.ProfileCommandService;
import com.denunciayabackend.profiles.infrastructure.persistence.jpa.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Profile Command Service Implementation
 */
@Service
@Transactional  // ✅ Esto asegura que los cambios se persistan
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        var emailAddress = new EmailAddress(command.email());

        // Validar si ya existe un perfil con ese email o userId
        if (profileRepository.existsByEmailAddress(emailAddress)) {
            throw new IllegalArgumentException("Profile with email address already exists");
        }
        if (profileRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("Profile with userId already exists");
        }

        // Crear y guardar perfil
        var profile = new Profile(command);
        profileRepository.save(profile);

        return Optional.of(profile);
    }
}
