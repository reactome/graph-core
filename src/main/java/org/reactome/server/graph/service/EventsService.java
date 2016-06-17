package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.repository.EventsRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Service
@SuppressWarnings("WeakerAccess")
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    public Collection<Collection<Pathway>> getEventAncestors(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventsRepository.getEventAncestorsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return eventsRepository.getEventAncestorsByDbId(Long.parseLong(id));
        }
        return null;
    }

}
