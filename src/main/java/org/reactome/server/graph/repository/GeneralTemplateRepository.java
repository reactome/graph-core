package org.reactome.server.graph.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@Repository
@SuppressWarnings("unused")
public class GeneralTemplateRepository {

    private static final Logger logger = LoggerFactory.getLogger(GeneralTemplateRepository.class);

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Autowired
    public GeneralTemplateRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }


    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    @Deprecated
    public Result query(String query, Map<String,Object> map) {
        // TODO No services are calling this.
        return null;
    }

    // TODO Test this
    public <T> T query(String query, Map<String,Object> map, Class<T> _clazz) {
        return (T) neo4jClient.query(query).bindAll(map).fetchAs(_clazz);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

    public <T extends DatabaseObject> T save(T t) {
        return neo4jTemplate.save(t);
    }

    // TODO See usage
    @Deprecated
    public <T extends DatabaseObject> T save(T t, int depth) {
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

    // TODO test delete
    public void delete(Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:$dbId}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        neo4jClient.query(query).bindAll(map).run();
    }

    // TODO test delete
    public void delete(String stId) {
        String query = "MATCH (n:DatabaseObject{stId:$stId}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        neo4jClient.query(query).bindAll(map).run();
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------

    public boolean fitForService() {
        String query = "MATCH (n) RETURN COUNT(n) > 0 AS fitForService";
        try {
            return neo4jClient.query(query).fetchAs(Boolean.class).first().orElse(false);
        } catch (Exception e) {
            logger.error("A connection with the Neo4j Graph could not be established. Tests will be skipped", e);
        }
        return false;
    }

    @Deprecated
    public void clearCache() {}

}
