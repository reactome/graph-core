package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.repository.MappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("WeakerAccess")
@Service
public class MappingService {

    private final MappingRepository mappingRepository;
    private final SpeciesService speciesService;

    @Autowired
    public MappingService(MappingRepository mappingRepository, SpeciesService speciesService) {
        this.mappingRepository = mappingRepository;
        this.speciesService = speciesService;
    }

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

    public Collection<Pathway> getGoPathways(String identifier) {
        if (identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getGoPathways(identifier);
        }
        return new ArrayList<>();
    }

    public Collection<Pathway> getGoPathways(String identifier, Object species) {
        Species s = speciesService.getSpecies(species);
        if (s != null  && identifier != null && !identifier.isEmpty()) {
            return mappingRepository.getGoPathways(identifier, s.getTaxId());
        }
        return new ArrayList<>();
    }

}
