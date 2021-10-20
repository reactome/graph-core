package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class CRUDRepository {

    private static final Logger logger = LoggerFactory.getLogger(CRUDRepository.class);

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public CRUDRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public Collection<Map<String, Object>> query(String query, Map<String,Object> map) {
        return this.neo4jClient.query(query).in(databaseName).bindAll(map).fetch().all();
    }
    
    public <T extends DatabaseObject> T query(String query, Map<String,Object> map, Class<T> _clazz) {
        return (T) neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(_clazz);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

    public <T extends DatabaseObject> T save(T t) {
        return neo4jTemplate.save(t);
    }

    public void deleteAllByClass(Class<?> _clazz)  {
        neo4jTemplate.deleteAll(_clazz);
    }

    public void delete(Object id, Class<?> _clazz)  {
        neo4jTemplate.deleteById(id, _clazz);
    }

    public <T extends DatabaseObject> void delete(T o)  {
        delete(o.getDbId());
    }

    public void delete(Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:$dbId}) OPTIONAL MATCH (n)-[r]-() DELETE n, r";
        neo4jClient.query(query).in(databaseName).bindAll(Map.of("dbId", dbId)).run();
    }

    public void delete(String stId) {
        String query = "MATCH (n:DatabaseObject{stId:$stId}) OPTIONAL MATCH (n)-[r]-() DELETE n, r";
        neo4jClient.query(query).in(databaseName).bindAll(Map.of("stId", stId)).run();
    }
    
}
