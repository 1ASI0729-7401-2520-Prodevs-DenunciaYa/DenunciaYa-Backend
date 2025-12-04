package com.denunciayabackend.authoritiesPanel.domain.services;

import com.denunciayabackend.authoritiesPanel.domain.model.queries.*;

import java.util.List;

public interface ResponsibleQueryService {

    record ResponsibleService(
            Long id,
            String businessId,
            String firstName,
            String lastName,
            String fullName,
            String email,
            String phone,
            String role,
            String description,
            String accessLevel,
            String status,
            String position,
            String department
    ) {}

    List<ResponsibleService> handle(GetAllResponsibleQuery query);
    ResponsibleService handle(GetResponsibleByIdQuery query);
    List<ResponsibleService> handle(GetResponsibleWithComplaintCountQuery query);
    List<ResponsibleService> handle(SearchResponsibleQuery query);
    List<ResponsibleService> handle(GetResponsiblesByStatusQuery query);
    List<ResponsibleService> handle(GetResponsiblesByAccessLevelQuery query);
}