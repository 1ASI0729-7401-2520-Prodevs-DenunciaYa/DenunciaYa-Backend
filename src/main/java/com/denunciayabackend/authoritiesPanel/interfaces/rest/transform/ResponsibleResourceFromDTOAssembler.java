package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleQueryService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ResponsibleResource;

public class ResponsibleResourceFromDTOAssembler {

    public static ResponsibleResource fromDTO(ResponsibleQueryService.ResponsibleDTO dto) {
        return new ResponsibleResource(
                dto.id(),
                dto.fullName(),
                dto.email(),
                dto.phone(),
                dto.role(),
                dto.assignedComplaintsCount()
        );
    }
}