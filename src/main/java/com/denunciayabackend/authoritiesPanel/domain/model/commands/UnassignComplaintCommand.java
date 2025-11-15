package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ComplaintId;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ResponsibleId;

public record UnassignComplaintCommand(
        Long responsibleId,
        Long complaintId
) {

    public UnassignComplaintCommand {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
        if (complaintId == null || complaintId < 1) {
            throw new IllegalArgumentException("ComplaintId cannot be null or less than 1.");
        }
    }

    public ResponsibleId getResponsibleIdVO() {
        return new ResponsibleId(responsibleId);
    }

    public ComplaintId getComplaintIdVO() {
        return new ComplaintId(complaintId);
    }
}
