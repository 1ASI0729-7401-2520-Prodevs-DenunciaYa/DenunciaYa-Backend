package com.denunciayabackend.authoritiesPanel.application.internal.queryservices;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.*;
import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleQueryService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ResponsibleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponsibleQueryServiceImpl implements ResponsibleQueryService {

    private final ResponsibleRepository responsibleRepository;

    public ResponsibleQueryServiceImpl(ResponsibleRepository responsibleRepository) {
        this.responsibleRepository = responsibleRepository;
    }

    @Override
    public List<ResponsibleService> handle(GetAllResponsibleQuery query) {
        return responsibleRepository.findAll()
                .stream()
                .map(this::toService)
                .collect(Collectors.toList());
    }

    @Override
    public ResponsibleService handle(GetResponsibleByIdQuery query) {
        return responsibleRepository.findById(query.responsibleId())
                .map(this::toService)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Responsible with id %s not found".formatted(query.responsibleId())
                ));
    }

    @Override
    public List<ResponsibleService> handle(GetResponsibleWithComplaintCountQuery query) {
        return responsibleRepository.findAll()
                .stream()
                .map(this::toService)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponsibleService> handle(SearchResponsibleQuery query) {
        String keywordLower = query.keyword().toLowerCase();

        return responsibleRepository.findAll()
                .stream()
                .filter(r ->
                        r.getFirstName().getValue().toLowerCase().contains(keywordLower) ||
                                r.getLastName().getValue().toLowerCase().contains(keywordLower) ||
                                r.getFullName().toLowerCase().contains(keywordLower) ||
                                r.getRole().getValue().toLowerCase().contains(keywordLower)
                )
                .map(this::toService)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponsibleService> handle(GetResponsiblesByStatusQuery query) {
        return responsibleRepository.findAll()
                .stream()
                .filter(r -> r.getStatusResponsible().name().equalsIgnoreCase(query.status()))
                .map(this::toService)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponsibleService> handle(GetResponsiblesByAccessLevelQuery query) {
        return responsibleRepository.findAll()
                .stream()
                .filter(r -> r.getAccessLevel().name().equalsIgnoreCase(query.accessLevel()))
                .map(this::toService)
                .collect(Collectors.toList());
    }

    private ResponsibleService toService(Responsible responsible) {
        return new ResponsibleService(
                responsible.getId(),
                responsible.getResponsibleId(),  // Este método retorna String
                responsible.getFirstName().getValue(),
                responsible.getLastName().getValue(),
                responsible.getFullName(),
                responsible.getEmail().getValue(),
                responsible.getPhoneNumber().getValue(),
                responsible.getRole().getValue(),
                responsible.getDescription() != null ? responsible.getDescription().getValue() : "",
                responsible.getAccessLevel() != null ? responsible.getAccessLevel().name() : "",
                responsible.getStatusResponsible() != null ? responsible.getStatusResponsible().name() : "",
                responsible.getPosition() != null ? responsible.getPosition().getValue() : "",
                responsible.getDepartment() != null ? responsible.getDepartment().getValue() : ""
        );
    }
}