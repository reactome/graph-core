package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Species;
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

    @Autowired
    public SpeciesService speciesService;

    public Collection<Event> getContainedEvents(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwaysRepository.getContainedEventsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwaysRepository.getContainedEventsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysFor(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysForByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysForByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysForByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysForByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysForAllFormsOf(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysForAllFormsOfByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysForAllFormsOfByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysForAllFormsOfByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysForAllFormsOfByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramFor(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysWithDiagramForByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysWithDiagramForByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysWithDiagramForByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysWithDiagramForByDbId(Long.parseLong(id));
            }
        }

        return null;
    }

    public Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOf(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwaysRepository.getPathwaysWithDiagramForAllFormsOfByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    public Collection<SimpleDatabaseObject> getLowerLevelPathwaysForIdentifier(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        if (s != null) {
            return pathwaysRepository.getLowerLevelPathwaysForIdentifierAndSpeciesTaxId(identifier, s.getTaxId());
        } else {
            return pathwaysRepository.getLowerLevelPathwaysForIdentifier(identifier);
        }
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
