package com.denunciayabackend.authoritiesPanel.domain.model.events;

import java.time.LocalDateTime;

/**
 * Base abstract domain event for all complaint assignment-related events.
 * <p>
 * This event captures essential metadata such as:
 * <ul>
 *   <li>A unique event identifier</li>
 *   <li>The timestamp when the event occurred</li>
 *   <li>The identifier of the aggregate (assignment) that generated the event</li>
 * </ul>
 * All specific assignment events (assigned, reassigned, completed, etc.)
 * must extend this class.
 */
public abstract class AssignmentEvent {

    /**
     * Unique identifier for the event. Automatically generated.
     */
    private final String eventId;

    /**
     * The timestamp when the event occurred.
     */
    private final LocalDateTime occurredOn;

    /**
     * The identifier of the aggregate root (ComplaintAssignment)
     * that produced this event.
     */
    private final String aggregateId;

    /**
     * Creates a new domain event associated with a specific assignment aggregate.
     *
     * @param aggregateId the ID of the aggregate root that generated the event
     */
    protected AssignmentEvent(String aggregateId) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.occurredOn = LocalDateTime.now();
        this.aggregateId = aggregateId;
    }

    /**
     * Returns the unique identifier of this event.
     *
     * @return the event ID
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Returns the timestamp indicating when the event occurred.
     *
     * @return the occurrence time
     */
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    /**
     * Returns the ID of the aggregate that emitted this event.
     *
     * @return the aggregate ID
     */
    public String getAggregateId() {
        return aggregateId;
    }
}