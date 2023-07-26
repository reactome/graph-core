package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.UpdateTracker;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpdateTrackerRepository extends Neo4jRepository<UpdateTracker, Long> {

    @Override
    Optional<UpdateTracker> findById(Long dbId);

    List<UpdateTracker> findByReleaseReleaseNumber(@Param("releaseNumber") int releaseNumber);

    List<UpdateTracker> findByUpdatedInstanceStId(@Param("stId") String stId);

    List<UpdateTracker> findByUpdatedInstanceDbId(@Param("dbId") Long dbId);

}
