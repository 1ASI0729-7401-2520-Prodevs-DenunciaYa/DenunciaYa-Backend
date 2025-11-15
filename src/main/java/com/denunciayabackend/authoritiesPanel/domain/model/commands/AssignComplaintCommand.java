package com.denunciayabackend.authoritiesPanel.domain.model.commands;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ComplaintId;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ResponsibleId;

/**
 * Command for assigning a complaint to a responsible.
 */
public record AssignComplaintCommand(
        Long responsibleId,
        Long complaintId
) {

    public AssignComplaintCommand {
        if (responsibleId == null || responsibleId < 1) {
            throw new IllegalArgumentException("ResponsibleId cannot be null or less than 1.");
        }
        if (complaintId == null || complaintId < 1) {
            throw new IllegalArgumentException("ComplaintId cannot be null or less than 1.");
        }
    }

    /**
     * Optional: Converts the Long to a Value Object if needed within domain logic.
     */
    public ResponsibleId getResponsibleIdVO() {
        return new ResponsibleId(responsibleId);
    }

    /**
     * Optional: Converts the Long to a Value Object if needed within domain logic.
     */
    public ComplaintId getComplaintIdVO() {
        return new ComplaintId(complaintId);
    }
}
