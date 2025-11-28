package com.denunciayabackend.iam.application.internal.queryservices;


import com.denunciayabackend.iam.domain.model.aggregates.User;
import com.denunciayabackend.iam.domain.model.queries.GetAllUsersQuery;
import com.denunciayabackend.iam.domain.model.queries.GetUserByIdQuery;
import com.denunciayabackend.iam.domain.model.queries.GetUserByUsernameQuery;
import com.denunciayabackend.iam.domain.services.UserQueryService;
import com.denunciayabackend.iam.infraestructure.persistence.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;


    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // inherited javadoc
    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    // inherited javadoc
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    // inherited javadoc
    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}
