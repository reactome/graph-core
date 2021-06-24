package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.repository.PathwayRepository;
import org.reactome.server.graph.repository.SimpleDatabaseObjectRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 */
@Service
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
        return ServiceUtils.fetchById(identifier, true, eventRepository::getContainedEventsByStId, eventRepository::getContainedEventsByDbId);
    }

    public Collection<Pathway> getPathwaysFor(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        String taxId = s != null ? s.getTaxId() : null;

        return ServiceUtils.fetchById(identifier, taxId, true,
                pathwayRepository::getPathwaysForByStIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysForByStId,
                pathwayRepository::getPathwaysForByDbIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysForByDbId);
    }

    public Collection<Pathway> getPathwaysForAllFormsOf(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        String taxId = s != null ? s.getTaxId() : null;

        return ServiceUtils.fetchById(identifier, taxId, true,
                pathwayRepository::getPathwaysForAllFormsOfByStIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysForAllFormsOfByStId,
                pathwayRepository::getPathwaysForAllFormsOfByDbIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysForAllFormsOfByDbId);
    }

    public Collection<Pathway> getPathwaysWithDiagramFor(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        String taxId = s != null ? s.getTaxId() : null;

        return ServiceUtils.fetchById(identifier, taxId, true,
                pathwayRepository::getPathwaysWithDiagramForByStIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysWithDiagramForByStId,
                pathwayRepository::getPathwaysWithDiagramForByDbIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysWithDiagramForByDbId);
    }

    public Collection<Pathway> getPathwaysWithDiagramForAllFormsOf(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        String taxId = s != null ? s.getTaxId() : null;

        return ServiceUtils.fetchById(identifier, taxId, true,
                pathwayRepository::getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysWithDiagramForAllFormsOfByStId,
                pathwayRepository::getPathwaysWithDiagramForAllFormsOfByDbIdAndSpeciesTaxId,
                pathwayRepository::getPathwaysWithDiagramForAllFormsOfByDbId);
    }

    @SuppressWarnings("unused")
    public Collection<Pathway> getLowerLevelPathwaysIncludingEncapsulation(Object identifier) {
        return ServiceUtils.fetchById(identifier, true,
                pathwayRepository::getLowerLevelPathwaysIncludingEncapsulation,
                pathwayRepository::getLowerLevelPathwaysIncludingEncapsulation);
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
        return ServiceUtils.fetchById(pathway, identifier, true,
                simpleDatabaseObjectRepository::getDiagramEntitiesForIdentifierByStId,
                simpleDatabaseObjectRepository::getDiagramEntitiesForIdentifierByDbId);
    }
}
