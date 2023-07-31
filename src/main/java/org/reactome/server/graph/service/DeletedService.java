package org.reactome.server.graph.service;


import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.repository.DeletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeletedService {

    private DeletedRepository repository;

    @Autowired
    public DeletedService(DeletedRepository repository) {
        this.repository = repository;
    }

    public Optional<Deleted> getDeletedByDbId(Long dbId) {
        return this.repository.getDeletedByDbId(dbId);
    }

    public List<Deleted> getByDeletedInstanceDbId(Long dbId) {
        return this.repository.getByDeletedInstanceDbId(dbId);
    }

    public List<Deleted> getByReplacementStId(String stId) {
        return this.repository.getByReplacementStId(stId);
    }

}
