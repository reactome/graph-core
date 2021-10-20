package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.OrthologyRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SuppressWarnings("WeakerAccess")
public class OrthologyService {

    private final OrthologyRepository orthologyRepository;

    @Autowired
    public OrthologyService(OrthologyRepository orthologyRepository) {
        this.orthologyRepository = orthologyRepository;
    }

    public Collection<DatabaseObject> getOrthology(Object identifier, Long speciesId){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return orthologyRepository.getOrthology(id, speciesId);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return orthologyRepository.getOrthology(Long.parseLong(id), speciesId);
        }
        return null;
    }

    public Map<Object, Collection<DatabaseObject>> getOrthologies(Collection<Object> identifiers, Long speciesId){
        Map<Object, Collection<DatabaseObject>> rtn = new HashMap<>();
        for (Object identifier : identifiers) {
            Collection<DatabaseObject> orthology = getOrthology(identifier, speciesId);
            if(orthology != null){
                rtn.put(identifier, orthology);
            }
        }
        return rtn;
    }
}
