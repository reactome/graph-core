package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DeletedInstance;
import org.reactome.server.graph.repository.DeletedInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeletedInstanceService {

    private DeletedInstanceRepository repository;

    @Autowired
    public DeletedInstanceService(DeletedInstanceRepository repository) {
        this.repository = repository;
    }

    public Optional<DeletedInstance> getDeletedInstanceByDbId(Long dbId) {
        return this.repository.getDeletedInstanceByDbId(dbId);
    }

    public List<DeletedInstance> getBYDeletedDbId(Long dbId) {
        return this.repository.getByDeletedDbId(dbId);
    }
}
