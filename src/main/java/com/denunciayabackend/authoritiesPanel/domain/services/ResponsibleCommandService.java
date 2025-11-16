package com.denunciayabackend.authoritiesPanel.domain.services;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;

public interface ResponsibleCommandService {

    /**
     * Handles the creation of a new Responsible.
     *
     * @param command the CreateResponsibleCommand
     * @return the generated ResponsibleId
     */
    Long handle(CreateResponsibleCommand command);

    /**
     * Handles the deletion of an existing Responsible.
     *
     * @param command the DeleteResponsibleCommand
     */
    void handle(DeleteResponsibleCommand command);

    /**
     * Handles the update of an existing Responsible's profile.
     *
     * @param command the UpdateResponsibleProfileCommand
     */
    void handle(UpdateResponsibleProfileCommand command);

    /**
     * Handles assigning a complaint to a Responsible.
     *
     * @param command the AssignComplaintCommand
     */
    void handle(AssignComplaintCommand command);

    /**
     * Handles unassigning a complaint from a Responsible.
     *
     * @param command the UnassignComplaintCommand
     */
    void handle(UnassignComplaintCommand command);
}