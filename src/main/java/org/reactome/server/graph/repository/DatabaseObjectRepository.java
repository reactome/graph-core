package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("ALL")
@Repository
public class DatabaseObjectRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public DatabaseObjectRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public <T extends DatabaseObject> T findByDbId(Long dbId) {
        String query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r]-(m) RETURN a, COLLECT(r), COLLECT(m)";
        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findByStId(String stId) {
        String query = "MATCH (a:DatabaseObject{stId:$stId})-[r]-(m) RETURN a, COLLECT(r), COLLECT(m)";
        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    public String findNewStId(String oldStId) {
        String query = "MATCH (n:DatabaseObject{oldStId:$oldStId}) RETURN n.stId";
        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("oldStId", oldStId)).fetchAs(String.class).one().orElse(null);
    }

    public <T extends DatabaseObject> T findByDbIdNoRelations(Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:$dbId}) RETURN n";
        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findByStIdNoRelations(String stId) {
        String query = "MATCH (n:DatabaseObject{stId:$stId}) RETURN n";
        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> Collection<T> findByDbIdsNoRelations(Collection<Long> dbIds) {
        String query = "MATCH (n:DatabaseObject) WHERE n.dbId IN $dbIds RETURN n";
        return (Collection<T>) neo4jTemplate.findAll(query, Map.of("dbIds", dbIds), DatabaseObject.class);
    }

    public <T extends DatabaseObject> Collection<T> findByStIdsNoRelations(Collection<String> stIds) {
        String query = "MATCH (n:DatabaseObject) WHERE n.stId IN $stIds RETURN n";
        return (Collection<T>) neo4jTemplate.findAll(query, Map.of("stIds", stIds), DatabaseObject.class);
    }
}
