package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.FireworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Service
public class FireworksService {

    @Autowired
    public FireworksRepository fireworksRepository;

    public Collection<SimpleDatabaseObject> getPathwaysFor(String stId, Long speciesId){
        return fireworksRepository.getPathwaysFor(stId, speciesId);
    }

    public Collection<SimpleDatabaseObject> getPathwaysForAllFormsOf(String stId, Long speciesId){
        return fireworksRepository.getPathwaysForAllFormsOf(stId, speciesId);
    }
}
