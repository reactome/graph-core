package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.repository.PathwayRepository;
import org.reactome.server.graph.repository.SimpleDatabaseObjectRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**

 */
@Service
@SuppressWarnings("WeakerAccess")
public class PathwaysService {

    private final EventRepository eventRepository;
    private final PathwayRepository pathwayRepository;
    private final SpeciesService speciesService;
    private final SimpleDatabaseObjectRepository simpleDatabaseObjectRepository;


    @Autowired
    public PathwaysService(EventRepository eventRepository, PathwayRepository pathwayRepository, SpeciesService speciesService, SimpleDatabaseObjectRepository simpleDatabaseObjectRepository) {
        this.eventRepository = eventRepository;
        this.pathwayRepository = pathwayRepository;
        this.speciesService = speciesService;
        this.simpleDatabaseObjectRepository = simpleDatabaseObjectRepository;
    }

    public Collection<Event> getContainedEvents(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventRepository.getContainedEventsByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return eventRepository.getContainedEventsByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Collection<Pathway> getPathwaysFor(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysForByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysForByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysForByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysForByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    public Collection<Pathway> getPathwaysForAllFormsOf(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysForAllFormsOfByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysForAllFormsOfByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysForAllFormsOfByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysForAllFormsOfByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    public Collection<Pathway> getPathwaysWithDiagramFor(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysWithDiagramForByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysWithDiagramForByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysWithDiagramForByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysWithDiagramForByDbId(Long.parseLong(id));
            }
        }

        return null;
    }

    public Collection<Pathway> getPathwaysWithDiagramForAllFormsOf(String identifier, Object species) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        Species s = speciesService.getSpecies(species);

        if (DatabaseObjectUtils.isStId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId(id, s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysWithDiagramForAllFormsOfByStId(id);
            }
        } else if (DatabaseObjectUtils.isDbId(id)) {
            if (s != null) {
                return pathwayRepository.getPathwaysWithDiagramForAllFormsOfByDbIdAndSpeciesTaxId(Long.parseLong(id), s.getTaxId());
            } else {
                return pathwayRepository.getPathwaysWithDiagramForAllFormsOfByDbId(Long.parseLong(id));
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public Collection<Pathway> getLowerLevelPathwaysIncludingEncapsulation(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return pathwayRepository.getLowerLevelPathwaysIncludingEncapsulation(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return pathwayRepository.getLowerLevelPathwaysIncludingEncapsulation(Long.parseLong(id));
        }
        return null;
    }

    public Collection<Pathway> getLowerLevelPathwaysForIdentifier(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        if (s != null) {
            return pathwayRepository.getLowerLevelPathwaysForIdentifierAndSpeciesTaxId(identifier, s.getTaxId());
        } else {
            return pathwayRepository.getLowerLevelPathwaysForIdentifier(identifier);
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
            aux = simpleDatabaseObjectRepository.getPathwaysForIdentifierByStId(identifier, stIds);
            if (aux != null && !aux.isEmpty()) rtn.addAll(aux);
        }
        if (!dbIds.isEmpty()) {
            aux = simpleDatabaseObjectRepository.getPathwaysForIdentifierByDbId(identifier, dbIds);
            if (aux != null && !aux.isEmpty()) rtn.addAll(aux);
        }

        if (rtn.isEmpty()) return null;
        return rtn;
    }

    public Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifier(String pathway, String identifier) {
        String id = DatabaseObjectUtils.getIdentifier(pathway);
        if (DatabaseObjectUtils.isStId(id)) {
            return simpleDatabaseObjectRepository.getDiagramEntitiesForIdentifierByStId(pathway, identifier);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return simpleDatabaseObjectRepository.getDiagramEntitiesForIdentifierByDbId(Long.parseLong(pathway), identifier);
        }
        return null;
    }
}
