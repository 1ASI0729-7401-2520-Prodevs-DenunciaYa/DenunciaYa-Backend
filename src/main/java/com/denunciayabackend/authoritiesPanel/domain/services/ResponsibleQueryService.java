package com.denunciayabackend.authoritiesPanel.domain.services;

import com.denunciayabackend.authoritiesPanel.domain.model.queries.*;

import java.util.List;

/**
 * Service interface for handling Responsible-related queries.
 */
public interface ResponsibleQueryService {

    /**
     * Retrieves all responsibles.
     */
    List<ResponsibleService> handle(GetAllResponsibleQuery query);

    /**
     * Retrieves a responsible by its ID.
     */
    ResponsibleService handle(GetResponsibleByIdQuery query);

    /**
     * Retrieves all responsibles with complaint count.
     */
    List<ResponsibleService> handle(GetResponsibleWithComplaintCountQuery query);

    /**
     * Searches responsibles by keyword (name or role).
     */
    List<ResponsibleService> handle(SearchResponsibleQuery query);

    /**
     * Returned to the frontend.
     */
    record ResponsibleService(
            Long id, // Cambiado de Long a String
            String firstName,
            String lastName,
            String fullName,
            String email,
            String phone,
            String role,
            String description,
            String accessLevel,
            String statusResponsible,
            String position,
            String department
    ) { }

}