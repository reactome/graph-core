package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrthologyRepository extends Neo4jRepository<DatabaseObject, Long> {

    //The relationship do not have direction because that's what is needed in this case
    @Query(" MATCH (:DatabaseObject{dbId:$dbId})<-[:inferredTo]-()-[:inferredTo]->(o:DatabaseObject)-[:species]->(:Species{dbId:$speciesId}) RETURN DISTINCT o " +
            "UNION " +
            "MATCH (:DatabaseObject{dbId:$dbId})-[:inferredTo]-(o:DatabaseObject)-[:species]->(:Species{dbId:$speciesId}) RETURN DISTINCT o")
    Collection<DatabaseObject> getOrthology(@Param("dbId") Long dbId, @Param("speciesId") Long speciesId);

    //The relationship do not have direction because that's what is needed in this case
    @Query(" MATCH (:DatabaseObject{stId:$stId})<-[:inferredTo]-()-[:inferredTo]->(o:DatabaseObject)-[:species]->(:Species{dbId:$speciesId}) RETURN DISTINCT o " +
            "UNION " +
            "MATCH (:DatabaseObject{stId:$stId})-[:inferredTo]-(o:DatabaseObject)-[:species]->(:Species{dbId:$speciesId}) RETURN DISTINCT o")
    Collection<DatabaseObject> getOrthology(@Param("stId") String stId, @Param("speciesId") Long speciesId);
}
