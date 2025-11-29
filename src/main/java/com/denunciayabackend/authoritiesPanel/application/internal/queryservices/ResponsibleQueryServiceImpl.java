package com.denunciayabackend.authoritiesPanel.application.internal.queryservices;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetAllResponsibleQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetResponsibleByIdQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetResponsibleWithComplaintCountQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.SearchResponsibleQuery;
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
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponsibleService handle(GetResponsibleByIdQuery query) {
        Long id = query.responsibleId(); // ← EXTRAEMOS el Long directamente

        return responsibleRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Responsible with id %s not found".formatted(id)
                ));
    }

    @Override
    public List<ResponsibleService> handle(GetResponsibleWithComplaintCountQuery query) {
        return responsibleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponsibleService> handle(SearchResponsibleQuery query) {
        String keywordLower = query.keyword().toLowerCase();

        return responsibleRepository.findAll()
                .stream()
                .filter(r ->
                        r.getFullName().firstName().toLowerCase().contains(keywordLower) ||
                                r.getFullName().lastName().toLowerCase().contains(keywordLower) ||
                                r.getRole().role().toLowerCase().contains(keywordLower)
                )
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResponsibleService toDTO(Responsible responsible) {
        return new ResponsibleService(
                responsible.getId(),
                responsible.getFullName().firstName() + " " + responsible.getFullName().lastName(),
                responsible.getEmail().email(),
                responsible.getPhoneNumber().phoneNumber(),
                responsible.getRole().role(),
                responsible.getAssignedComplaints().size()
        );
    }
}
