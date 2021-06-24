package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.reactome.server.graph.repository.EventAncestorsRepository;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EventsService {
    private final EventAncestorsRepository eventAncestorsRepository;

    @Autowired
    public EventsService(EventAncestorsRepository eventAncestorsRepository) {
        this.eventAncestorsRepository = eventAncestorsRepository;
    }

    /**
     * @return returns a List of Event as it can contain Reactions and Pathway.
     */
    public Collection<EventProjectionWrapper> getEventAncestors(Object identifier) {
        return ServiceUtils.fetchById(identifier, true, eventAncestorsRepository::getEventAncestorsByStId, eventAncestorsRepository::getEventAncestorsByDbId);
    }
}
