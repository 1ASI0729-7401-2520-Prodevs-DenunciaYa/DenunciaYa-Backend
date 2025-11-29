package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleQueryService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ResponsibleResource;

public class ResponsibleResourceFromAssembler {

    public static ResponsibleResource fromDTO(ResponsibleQueryService.ResponsibleService dto) {
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