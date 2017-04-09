package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface DatabaseObjectRepository extends GraphRepository<DatabaseObject>{

    //Derived queries
    <T extends DatabaseObject> T   findByDbId(Long dbId);
    <T extends DatabaseObject> T  findByStId(String stId);

    @Query("MATCH (n:DatabaseObject{oldStId:{0}}) RETURN n.stId")
    String findNewStId(String oldStIid);

    @Query("MATCH (n:DatabaseObject{dbId:{0}}) RETURN n")
    <T extends DatabaseObject> T findByDbIdNoRelations(Long dbId);

    @Query("MATCH (n:DatabaseObject{stId:{0}}) RETURN n")
    <T extends DatabaseObject> T findByStIdNoRelations(String stId);

    @Query("MATCH (n:DatabaseObject) WHERE n.dbId IN {0} RETURN n")
    <T extends DatabaseObject> Collection<T> findByDbIdsNoRelations(Collection<Long> dbIds);

    @Query("MATCH (n:DatabaseObject) WHERE n.stId IN {0} RETURN n")
    <T extends DatabaseObject> Collection<T> findByStIdsNoRelations(Collection<String> stId);

}
