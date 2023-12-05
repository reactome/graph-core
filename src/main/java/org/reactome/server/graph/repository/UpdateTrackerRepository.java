package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.UpdateTracker;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpdateTrackerRepository extends Neo4jRepository<UpdateTracker, Long> {

    //language=cypher
    @Query(" MATCH (d:UpdateTracker{dbId:$dbId}) " +
            "OPTIONAL MATCH (d)-[r]-(m) " +
            "RETURN d, collect(r), collect(m)")
    Optional<UpdateTracker> findUpdateTrackerByDbId(@Param("dbId") Long dbId);


    //language=cypher
    @Query(" MATCH(u:UpdateTracker)-[:updatedInstance]->(t:Trackable) " +
            "WHERE t.dbId = $dbId " +
            "RETURN u")
    List<UpdateTracker> findByUpdatedInstanceDbId(Long dbId);

    //language=cypher
    @Query(" MATCH(d:UpdateTracker)-[:updatedInstance]->(t:Trackable) " +
            "WHERE t.stId = $stId " +
            "RETURN d ")
    List<UpdateTracker> findByUpdatedInstanceStId(@Param("stId") String stId);

}
