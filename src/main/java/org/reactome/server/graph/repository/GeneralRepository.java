package org.reactome.server.graph.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
public class GeneralRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    public GeneralRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }
    
    public Boolean fitForService() {
        String query = "MATCH (n) RETURN COUNT(n) > 0 AS fitForService";
        return neo4jClient.query(query).in(databaseName).fetchAs(Boolean.class).first().orElse(false);
    }
}
