package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.reactome.server.graph.repository.EventAncestorsRepository;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EventsService {
    private final EventAncestorsRepository eventAncestorsRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventsService(EventAncestorsRepository eventAncestorsRepository, EventRepository eventRepository) {
        this.eventAncestorsRepository = eventAncestorsRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * @return returns a List of Event as it can contain Reactions and Pathway.
     */
    public Collection<EventProjectionWrapper> getEventAncestors(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventAncestorsRepository.getEventAncestorsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return eventAncestorsRepository.getEventAncestorsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Collection<EventProjectionWrapper> getUngroupedEventAncestors(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventAncestorsRepository.getEventAncestorsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return eventAncestorsRepository.getEventAncestorsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Optional<Event> getEventInDepth(Object identifier, int maxDepth) {
        return this.getEventInDepth(identifier, maxDepth, List.of("compartment", "species"));
    }

    public Optional<Event> getEventInDepth(Object identifier, int maxDepth, List<String> attributes) {
        if (maxDepth == 0) maxDepth = 1;
        if (maxDepth < 0) maxDepth = Integer.MAX_VALUE;
        if (attributes == null || attributes.isEmpty()) attributes = List.of("species");

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        String attributeString = String.join("|", attributes);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventRepository.getEventInDepth("stId", id, maxDepth, attributeString);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getEventInDepth("dbId", Long.parseLong(id), maxDepth, attributeString);
        }
        return Optional.empty();
    }
}
