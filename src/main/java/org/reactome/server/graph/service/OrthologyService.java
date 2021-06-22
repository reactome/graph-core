package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.OrthologyRepository;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrthologyService {

    private final OrthologyRepository orthologyRepository;

    @Autowired
    public OrthologyService(OrthologyRepository orthologyRepository) {
        this.orthologyRepository = orthologyRepository;
    }

    public Collection<DatabaseObject> getOrthology(Object identifier, Long speciesId) {
        return ServiceUtils.fetchById(identifier, speciesId, true, orthologyRepository::getOrthology, orthologyRepository::getOrthology);
    }

    public Map<Object, Collection<DatabaseObject>> getOrthologies(Collection<Object> identifiers, Long speciesId) {
        return identifiers.stream()
                .collect(Collectors.toMap(
                        identifier -> identifier,
                        identifier -> getOrthology(identifier, speciesId)));
    }
}
