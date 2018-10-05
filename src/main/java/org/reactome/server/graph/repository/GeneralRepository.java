package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DBInfo;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.04.16.
 */
@Repository
public interface GeneralRepository extends GraphRepository<DatabaseObject> {

    @Query(" MATCH (n:DatabaseObject) RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count")
    Collection<SchemaClassCount> getSchemaClassCounts();

    @Query("MATCH (db:DBInfo) RETURN db LIMIT 1")
    DBInfo getDBInfo();
}
