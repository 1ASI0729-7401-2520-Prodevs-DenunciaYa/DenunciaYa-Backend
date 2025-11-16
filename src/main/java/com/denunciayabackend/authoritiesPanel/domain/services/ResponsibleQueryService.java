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
    List<ResponsibleDTO> handle(GetAllResponsibleQuery query);

    /**
     * Retrieves a responsible by its ID.
     */
    ResponsibleDTO handle(GetResponsibleByIdQuery query);

    /**
     * Retrieves all responsibles with complaint count.
     */
    List<ResponsibleDTO> handle(GetResponsibleWithComplaintCountQuery query);

    /**
     * Searches responsibles by keyword (name or role).
     */
    List<ResponsibleDTO> handle(SearchResponsibleQuery query);

    /**
     * DTO returned to the frontend.
     */
    record ResponsibleDTO(
            Long id,
            String fullName,
            String email,
            String phone,
            String role,
            int assignedComplaintsCount
    ) { }
}
