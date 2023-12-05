package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DeletedInstance;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeletedInstanceRepository extends Neo4jRepository<DeletedInstance, Long> {
    //language=cypher
    @Query(" MATCH (d:DeletedInstance{dbId:$dbId}) " +
            "OPTIONAL MATCH (d)-[r]-(m) " +
            "RETURN d, collect(r), collect(m)")
    Optional<DeletedInstance> getDeletedInstanceByDbId(@Param("dbId") Long dbId);


    @Query("MATCH (d:DeletedInstance)<-[:deletedInstance]-(de:Deleted) " +
            "where de.dbId = $dbId " +
            "return d ")
    List<DeletedInstance> getByDeletedDbId(@Param("dbId") Long dbId);
}
