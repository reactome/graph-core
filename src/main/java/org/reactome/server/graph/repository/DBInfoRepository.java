package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DBInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DBInfoRepository extends Neo4jRepository<DBInfo, Long> {
    @Query("MATCH (db:DBInfo) RETURN db LIMIT 1")
    DBInfo getDBInfo();
}
