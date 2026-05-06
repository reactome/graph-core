package org.reactome.server.graph.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.Map;

@Configuration
public class MaxDepthConfig {
    Neo4jClient neo4jClient;


    private final Map<String, Runnable> maxDepthInitializers = Map.of(
            "PhysicalEntity", this::initializePhysicalEntityMaxDepth,
            "Event", this::initializeEventMaxDepth
    );

    @Autowired
    public MaxDepthConfig(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Bean
    ApplicationRunner checkMaxDepths() {
        return args -> maxDepthInitializers.forEach((nodeType, initializer) -> {
            Long missingMaxDepth = getNumberOfMissingMaxDepth(nodeType);
            if (missingMaxDepth > 0) {
                System.out.println("Initializing maxDepth for " + missingMaxDepth + " " + nodeType);
                initializer.run();
            }
        });
    }

    Long getNumberOfMissingMaxDepth(String nodeType) {
        if (!maxDepthInitializers.containsKey(nodeType))
            throw new IllegalArgumentException("Node type: " + nodeType + " doesn't support maxDepth");

        return neo4jClient.query(
                        //language=Cypher
                        "" +
                                "MATCH (o:" + nodeType + ") " +
                                "WHERE o.maxDepth IS NULL " +
                                "RETURN count(o) AS missing"
                )
                .fetchAs(Long.class)
                .mappedBy((typeSystem, record) -> record.get("missing").asLong())
                .one()
                .orElse(0L);
    }


    public void initializePhysicalEntityMaxDepth() {
        //Limit of 200 sets in case of circular references
        //language=Cypher
        neo4jClient.query("" +
                        "MATCH (o:PhysicalEntity) " +
                        "WHERE o.maxDepth IS NULL " +
                        "OPTIONAL MATCH path=(o)-[:hasComponent|hasMember|hasCandidate|repeatedUnit|proteinMarker|RNAMarker*1..200]->(:PhysicalEntity) " +
                        "WITH coalesce(max(length(path)) + 1, 1) AS maxDepth, o " +
                        "SET o.maxDepth = maxDepth").run();
    }

    public void initializeEventMaxDepth() {
        //Limit of 200 sets in case of circular references
        //language=Cypher
        neo4jClient.query("" +
                        "MATCH (o:Event) " +
                        "WHERE o.maxDepth IS NULL " +
                        "OPTIONAL MATCH path=(o)-[:hasEvent*1..200]->(:Event) " +
                        "WITH coalesce(max(length(path)) + 1, 1) AS maxDepth, o " +
                        "SET o.maxDepth = maxDepth").run();
    }
}
