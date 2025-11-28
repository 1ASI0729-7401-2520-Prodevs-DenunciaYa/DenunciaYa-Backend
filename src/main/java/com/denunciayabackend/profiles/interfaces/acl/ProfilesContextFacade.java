package com.denunciayabackend.profiles.interfaces.acl;

import java.time.LocalDate;

public interface ProfilesContextFacade {

    Long createProfile(String firstName, String lastName, String email,
                       LocalDate birthDate, String phoneNumber, Long userId);

    Long fetchProfileIdByEmail(String email);
}
