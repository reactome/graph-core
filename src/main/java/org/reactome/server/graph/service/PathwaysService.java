package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.PathwaysRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Service
@SuppressWarnings("WeakerAccess")
public class PathwaysService {

    @Autowired
    public PathwaysRepository pathwaysRepository;

    public Collection<Event> getContainedEvents(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getContainedEventsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getContainedEventsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysFor(String identifier, Long speciesId) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysForByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getPathwaysForByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysForAllFormsOf(String identifier, Long speciesId) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysForAllFormsOfByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getPathwaysForAllFormsOfByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramFor(String identifier, Long speciesId) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOf(String identifier, Long speciesId) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByStId(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByDbId(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getLowerLevelPathwaysForIdentifier(String identifier, Long speciesId) {
        return pathwaysRepository.getLowerLevelPathwaysForIdentifier(identifier, speciesId);
    }

    public Collection<SimpleDatabaseObject> getPathwaysForIdentifier(String identifier, String... pathways) {
        return getPathwaysForIdentifier(identifier, Arrays.asList(pathways));
    }

    public Collection<SimpleDatabaseObject> getPathwaysForIdentifier(String identifier, Collection<String> pathways) {
        //The user might submit a list where dbIds and stIds are mixed -> we create two lists
        Collection<String> stIds = new HashSet<>();
        Collection<Long> dbIds = new HashSet<>();
        for (String pathway : pathways) {
            String id = DatabaseObjectUtils.getIdentifier(pathway);
            if (DatabaseObjectUtils.isStId(id)) {
                stIds.add(id);
            } else if (DatabaseObjectUtils.isDbId(id)) {
                dbIds.add(Long.valueOf(id));
            }
        }

        Collection<SimpleDatabaseObject> rtn = new HashSet<>();
        //Aggregating the results in a set (if any). Order isn't taken into account for the time being
        Collection<SimpleDatabaseObject> aux;
        if (!stIds.isEmpty()) {
            aux = pathwaysRepository.getPathwaysForIdentifierByStId(identifier, stIds);
            if (aux != null && !aux.isEmpty()) rtn.addAll(aux);
        }
        if (!dbIds.isEmpty()) {
            aux = pathwaysRepository.getPathwaysForIdentifierByDbId(identifier, dbIds);
            if (aux != null && !aux.isEmpty()) rtn.addAll(aux);
        }

        if (rtn.isEmpty()) return null;
        return rtn;
    }

    public Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifier(String pathway, String identifier) {
        String id = DatabaseObjectUtils.getIdentifier(pathway);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getDiagramEntitiesForIdentifierByStId(pathway, identifier);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getDiagramEntitiesForIdentifierByDbId(Long.parseLong(pathway), identifier);
        }
        return null;
    }
}
