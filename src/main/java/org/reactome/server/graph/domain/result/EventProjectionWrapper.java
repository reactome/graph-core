package org.reactome.server.graph.domain.result;

import java.util.Collection;

/**
 * EventProjections is used to map a Collection of Events ancestors.
 * The original query prior to SDN6 was mapping the whole Pathway and only using
 * trivial attributes to be marshalled to JSON in the ContentService.
 */
public class EventProjectionWrapper {

    private Collection<EventProjection> events;

    public EventProjectionWrapper(Collection<EventProjection> events) {
        this.events = events;
    }

    public Collection<EventProjection> getEvents() {
        return events;
    }

    public void setEvents(Collection<EventProjection> events) {
        this.events = events;
    }
}

