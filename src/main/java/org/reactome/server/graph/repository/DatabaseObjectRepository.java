package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Repository
public class DatabaseObjectRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Autowired
    public DatabaseObjectRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public <T extends DatabaseObject> T findByDbId(@Param("dbId") Long dbId) {
        String query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r]->(m) RETURN a, COLLECT(r), COLLECT(m)";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbId", dbId);
        return (T) neo4jTemplate.findOne(query, params, DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findByStId(@Param("stId") String stId) {
        String query = "MATCH (a:DatabaseObject{stId:$stId})-[r]-(m) RETURN a, COLLECT(r), COLLECT(m)";
        Map<String, Object> params = new HashMap<>(1);
        params.put("stId", stId);
        return (T) neo4jTemplate.findOne(query, params, DatabaseObject.class).orElse(null);
    }

    public String findNewStId(String oldStId) {
        String query = "MATCH (n:DatabaseObject{oldStId:$oldStId}) RETURN n.stId";
        Map<String, Object> params = new HashMap<>(1);
        params.put("oldStId", oldStId);
        return neo4jClient.query(query).bindAll(params).fetchAs(String.class).one().orElse(null);
    }

    public <T extends DatabaseObject> T findByDbIdNoRelations(@Param("dbId") Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:$dbId}}) RETURN n";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbId", dbId);
        return (T) neo4jTemplate.findOne(query, params, DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findByStIdNoRelations(@Param("stId") String stId) {
        String query = "MATCH (n:DatabaseObject{stId:$stId}) RETURN n";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbId", stId);
        return (T) neo4jTemplate.findOne(query, params, DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> Collection<T> findByDbIdsNoRelations(Collection<Long> dbIds) {
        String query = "MATCH (n:DatabaseObject) WHERE n.dbId IN $dbIds RETURN n";
        Map<String, Object> params = new HashMap<>(1);
        params.put("dbIds", dbIds);
        return (Collection<T>) neo4jTemplate.findAll(query, params, DatabaseObject.class);
    }

    public <T extends DatabaseObject> Collection<T> findByStIdsNoRelations(Collection<String> stIds) {
        String query = "MATCH (n:DatabaseObject) WHERE n.stId IN $stIds RETURN n";
        Map<String, Object> params = new HashMap<>(1);
        params.put("stIds", stIds);
        return (Collection<T>) neo4jTemplate.findAll(query, params, DatabaseObject.class);
    }
}
