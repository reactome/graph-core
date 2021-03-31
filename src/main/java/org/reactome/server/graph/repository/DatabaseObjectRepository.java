package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface DatabaseObjectRepository extends Neo4jRepository<DatabaseObject, Long>{

    //Derived queries
    <T extends DatabaseObject> T findByDbId(Long dbId);

    @Query("MATCH (n:DatabaseObject{stId:$stId}) RETURN n")
    <T extends DatabaseObject> T findByStId(@Param("stId")String stId);

    @Query("MATCH (n:DatabaseObject{oldStId:{oldStId}}) RETURN n.stId")
    String findNewStId(String oldStId);

    @Query("MATCH (n:DatabaseObject{dbId:{dbId}}) RETURN n")
    <T extends DatabaseObject> T findByDbIdNoRelations(Long dbId);

    @Query("MATCH (n:DatabaseObject{stId:$stIda}) RETURN n")
    <T extends DatabaseObject> T findByStIdNoRelations(@Param("stIda") String stIda);

    @Query("MATCH (n:DatabaseObject) WHERE n.dbId IN {dbIds} RETURN n")
    <T extends DatabaseObject> Collection<T> findByDbIdsNoRelations(Collection<Long> dbIds);

    @Query("MATCH (n:DatabaseObject) WHERE n.stId IN $stId RETURN n")
    <T extends DatabaseObject> Collection<T> findByStIdsNoRelations(Collection<String> stId);

}
