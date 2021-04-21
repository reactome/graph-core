package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Repository
public class DatabaseObjectRepo {

    private final Neo4jTemplate neo4jTemplate;

    @Autowired
    public DatabaseObjectRepo(Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    public <T extends DatabaseObject> T findById(@Param("dbId") Long dbId) {
        String query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r]->(m) RETURN a,collect(r), collect(m)";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbId", dbId);
        return (T) neo4jTemplate.findOne(query, params, T.emptyObject().getClass()).orElse(null);
    }

    public <T extends DatabaseObject> T findEnhancedById(@Param("dbId") Long dbId) {
//        String query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r]-(m) OPTIONAL RETURN a,collect(r), collect(m)";
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH (n)-[r1]-(m) " +
                "OPTIONAL MATCH (m)-[r2:species]->(s) " +
                "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|physicalEntity|crossReference|referenceGene|literatureReference]-(o) " +
                "RETURN n,collect(r1),collect(m),collect(r2),collect(s),collect(r3),collect(o) ";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbId", dbId);
        return (T) neo4jTemplate.findOne(query, params, T.emptyObject().getClass()).orElse(null);
    }
//
//    @Query("MATCH (n:DatabaseObject{oldStId:$oldStId}) RETURN n.stId")
//    String findNewStId(@Param("oldStId") String oldStId);
//
//    @Query("MATCH (n:DatabaseObject{dbId:$dbId}}) RETURN n")
//    <T extends DatabaseObject> T findByDbIdNoRelations(@Param("dbId") Long dbId);
//
//    @Query("MATCH (n:DatabaseObject{stId:$stId}) RETURN n")
//    <T extends DatabaseObject> T findByStIdNoRelations(@Param("stId") String stIda);
//
//    @Query("MATCH (n:DatabaseObject) WHERE n.dbId IN $dbIds RETURN n")
//    <T extends DatabaseObject> Collection<T> findByDbIdsNoRelations(Collection<Long> dbIds);
//
//    @Query("MATCH (n:DatabaseObject) WHERE n.stId IN $stId RETURN n")
//    <T extends DatabaseObject> Collection<T> findByStIdsNoRelations(Collection<String> stId);
}
