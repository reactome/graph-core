package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.repository.MappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("WeakerAccess")
@Service
public class MappingService {

    private MappingRepository mappingRepository;

    private SpeciesService speciesService;

    public Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier) {
        if (databaseName != null && !databaseName.isEmpty() && identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getReactionsLikeEvent(databaseName, identifier);
        }
        return new ArrayList<>();
    }

    public Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        if (s != null && databaseName != null && !databaseName.isEmpty() && identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getReactionsLikeEvent(databaseName, identifier, s.getTaxId());
        }
        return new ArrayList<>();
    }

    public Collection<Pathway> getPathways(String databaseName, String identifier) {
        if (databaseName != null && !databaseName.isEmpty() && identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getPathways(databaseName, identifier);
        }
        return new ArrayList<>();
    }

    public Collection<Pathway> getPathways(String databaseName, String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        if (s != null && databaseName != null && !databaseName.isEmpty() && identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getPathways(databaseName, identifier, s.getTaxId());
        }
        return new ArrayList<>();
    }

    @Autowired
    public void setMappingRepository(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    @Autowired
    public void setSpeciesService(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }
}
