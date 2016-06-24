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

    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByStId(String stId);
    DatabaseObject findByOldStId(String oldStId);

    @Query("MATCH (n:DatabaseObject{dbId:{0}}) RETURN n")
    DatabaseObject findByDbIdNoRelations(Long dbId);

    @Query("MATCH (n:DatabaseObject{stId:{0}}) RETURN n")
    DatabaseObject findByStIdNoRelations(String stId);

    @Query("MATCH (n:DatabaseObject) WHERE n.dbId IN {0} RETURN n")
    Collection<DatabaseObject> findByDbIdsNoRelations(Collection<Long> dbIds);

    @Query("MATCH (n:DatabaseObject) WHERE n.stId IN {0} RETURN n")
    Collection<DatabaseObject> findByStIdsNoRelations(Collection<String> stId);

}
