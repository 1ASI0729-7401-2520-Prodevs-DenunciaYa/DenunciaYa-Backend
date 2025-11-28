package com.denunciayabackend.profiles.domain.model.queries;

import com.denunciayabackend.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {}
