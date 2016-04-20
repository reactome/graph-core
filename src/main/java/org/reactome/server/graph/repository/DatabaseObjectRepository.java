package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Repository
public interface DatabaseObjectRepository extends GraphRepository<DatabaseObject>{

    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByStableIdentifier(String stableIdentifier);

    @Query("MATCH (n:DatabaseObject{dbId:{0}}) RETURN n")
    DatabaseObject findByDbIdNoRelations(Long dbId);

    @Query("MATCH (n:DatabaseObject{stableIdentifier:{0}}) RETURN n")
    DatabaseObject findByStableIdentifierNoRelations(String stableIdentifier);

    @Query("MATCH (n:DatabaseObject) WHERE n.dbId IN {0} RETURN n")
    Collection<DatabaseObject> findByDbIdsNoRelations(Collection<Long> dbIds);

    @Query("MATCH (n:DatabaseObject) WHERE n.stableIdentifier IN {0} RETURN n")
    Collection<DatabaseObject> findByStableIdentifiersNoRelations(Collection<String> stableIdentifiers);
}
