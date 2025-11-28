package com.denunciayabackend.profiles.domain.services;


import com.denunciayabackend.profiles.domain.model.aggregates.Profile;
import com.denunciayabackend.profiles.domain.model.commands.CreateProfileCommand;

import java.util.Optional;
public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);

}
