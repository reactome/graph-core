package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Deleted;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DeletedRepository {


    private final Neo4jTemplate neo4jTemplate;

    public DeletedRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;

    }

    public Optional<Deleted> getDeletedByDbId(Long dbId) {
        String query = "MATCH (d:Deleted{dbId:$dbId}) OPTIONAL MATCH (d)-[r]-(m) RETURN d, COLLECT(r), COLLECT(m)";
        return neo4jTemplate.findOne(query, Map.of("dbId", dbId), Deleted.class);
    }


    public List<Deleted> getByDeletedInstanceDbId(Long dbId) {
        String query = "MATCH (p:Deleted)-[r:deletedInstance*]->(d:DeletedInstance) WHERE d.dbId = $dbId RETURN COLLECT(p)";
        return neo4jTemplate.findAll(query, Map.of("dbId", dbId), Deleted.class);
    }


    public List<Deleted> getByReplacementStId(String stId) {
        String query = "MATCH (p:Deleted)-[r:replacementInstances*]->(d:Deletable) WHERE d.stId = $stId  RETURN COLLECT(p)";
        return neo4jTemplate.findAll(query, Map.of("stId", stId), Deleted.class);
    }

}
