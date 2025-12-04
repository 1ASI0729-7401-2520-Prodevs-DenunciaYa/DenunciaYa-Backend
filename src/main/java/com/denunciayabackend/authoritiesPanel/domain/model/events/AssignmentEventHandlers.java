package com.denunciayabackend.authoritiesPanel.domain.model.events;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AssignmentEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentEventHandlers.class);

    @EventListener
    public void handleComplaintAssignedEvent(ComplaintAssignedEvent event) {
        logger.info("Denuncia asignada - Evento ID: {}, Denuncia: {}, Responsable: {}, Asignado por: {}, Notas: {}",
                event.getEventId(),
                event.getComplaintId(),
                event.getResponsibleId(),
                event.getAssignedBy(),
                event.getNotes());

    }

    @EventListener
    public void handleAssignmentStatusUpdatedEvent(AssignmentStatusUpdatedEvent event) {
        logger.info("Estado de asignación actualizado - Evento ID: {}, Asignación: {}, Estado anterior: {}, Nuevo estado: {}",
                event.getEventId(),
                event.getAggregateId(),
                event.getOldStatus(),
                event.getNewStatus());

        if (event.getNewStatus() == AssignmentStatus.COMPLETED) {
            logger.info("Asignación {} marcada como COMPLETADA", event.getAggregateId());
        } else if (event.getNewStatus() == AssignmentStatus.CANCELLED) {
            logger.info("Asignación {} CANCELADA", event.getAggregateId());
        }
    }

    @EventListener
    public void handleComplaintReassignedEvent(ComplaintReassignedEvent event) {
        logger.info("Denuncia reasignada - Evento ID: {}, Asignación: {}, Responsable anterior: {}, Nuevo responsable: {}",
                event.getEventId(),
                event.getAggregateId(),
                event.getOldResponsibleId(),
                event.getNewResponsibleId());


    }

    @EventListener
    public void handleAssignmentCompletedEvent(AssignmentCompletedEvent event) {
        logger.info("Asignación completada - Evento ID: {}, Denuncia: {}, Responsable: {}",
                event.getEventId(),
                event.getComplaintId(),
                event.getResponsibleId());

    }

    @EventListener
    public void handleAssignmentCancelledEvent(AssignmentCancelledEvent event) {
        logger.info("Asignación cancelada - Evento ID: {}, Denuncia: {}, Responsable: {}",
                event.getEventId(),
                event.getComplaintId(),
                event.getResponsibleId());


    }

    @EventListener
    public void handleAllAssignmentEvents(AssignmentEvent event) {
        logger.debug("Evento de asignación registrado: {} - {} en {}",
                event.getClass().getSimpleName(),
                event.getAggregateId(),
                event.getOccurredOn());

    }
}