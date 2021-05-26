package org.reactome.server.graph.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public GeneralTemplateRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    public Collection<Map<String, Object>> query(String query, Map<String,Object> map) {
        return this.neo4jClient.query(query).in(databaseName).bindAll(map).fetch().all();
    }

    // TODO Test this
    public <T> T query(String query, Map<String,Object> map, Class<T> _clazz) {
        return (T) neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(_clazz);
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------


    @Deprecated
    public void clearCache() {}

}
