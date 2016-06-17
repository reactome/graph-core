package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.PathwaysRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Service
@SuppressWarnings("WeakerAccess")
public class PathwaysService {

    @Autowired
    public PathwaysRepository pathwaysRepository;

    public Collection<Event> getContainedEvents(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getContainedEventsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return pathwaysRepository.getContainedEventsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysFor(String identifier, Long speciesId){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysForByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return pathwaysRepository.getPathwaysForByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysForAllFormsOf(String identifier, Long speciesId){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysForAllFormsOfByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return pathwaysRepository.getPathwaysForAllFormsOfByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramFor(String identifier, Long speciesId){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return pathwaysRepository.getPathwaysWithDiagramForByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOf(String identifier, Long speciesId){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

}
