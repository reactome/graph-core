package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.UpdateTracker;
import org.reactome.server.graph.repository.UpdateTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UpdateTrackerService {

    private UpdateTrackerRepository repository;

    @Autowired
    public UpdateTrackerService(UpdateTrackerRepository repository) {
        this.repository = repository;
    }


    public Optional<UpdateTracker> findUpdateTrackerByDbId(Long dbId) {
        return this.repository.findUpdateTrackerByDbId(dbId);
    }

    public List<UpdateTracker> findByUpdatedInstanceDbId(Long dbId) {
        return this.repository.findByUpdatedInstanceDbId(dbId);
    }

    public List<UpdateTracker> findByUpdatedInstanceStId(String stId) {
        return this.repository.findByUpdatedInstanceStId(stId);
    }

}
