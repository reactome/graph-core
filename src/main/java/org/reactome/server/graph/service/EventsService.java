package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.reactome.server.graph.repository.EventAncestorsRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
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
}
