package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class ParticipantRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public ParticipantRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<Participant> getParticipants(Long dbId) {
        String query = " " +
                "MATCH (n:DatabaseObject{dbId:$dbId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
                "OPTIONAL MATCH (m)-[:referenceEntity]->(re1:ReferenceEntity) " +
                "OPTIONAL MATCH (m)-[:hasMember|hasComponent|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity)-[:referenceEntity]->(re2:ReferenceEntity) " +
                "WITH DISTINCT m,  " +
                "              CASE WHEN re1 IS NULL THEN re2 ELSE re1 END AS re, " +
                "              CASE WHEN pe IS NULL THEN m.schemaClass ELSE pe.schemaClass END AS icon " +
                "WHERE NOT re IS NULL " +
                "RETURN m.dbId AS peDbId, " +
                "       m.displayName AS displayName, " +
                "       m.schemaClass AS schemaClass, " +
                "       COLLECT(DISTINCT({ " +
                "              dbId: re.dbId, " +
                "              displayName: re.displayName, " +
                "              identifier: CASE WHEN re.variantIdentifier IS NOT NULL THEN re.variantIdentifier ELSE re.identifier END, " +
                "              url: re.url, " +
                "              schemaClass: re.schemaClass, " +
                "              icon: icon " +
                "       })) AS refEntities";

        return neo4jClient.query(query).in(databaseName).bindAll(Collections.singletonMap("dbId", dbId)).fetchAs(Participant.class).mappedBy( (t, record) -> Participant.build(record)).all();
    }


    public Collection<Participant> getParticipants(String stId) {
        String query = " " +
                "MATCH (n:DatabaseObject{stId:$stId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
                "OPTIONAL MATCH (m)-[:referenceEntity]->(re1:ReferenceEntity) " +
                "OPTIONAL MATCH (m)-[:hasMember|hasComponent|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity)-[:referenceEntity]->(re2:ReferenceEntity) " +
                "WITH DISTINCT m, " +
                "              CASE WHEN re1 IS NULL THEN re2 ELSE re1 END AS re, " +
                "              CASE WHEN pe IS NULL THEN m.schemaClass ELSE pe.schemaClass END AS icon " +
                "WHERE NOT re IS NULL " +
                "RETURN m.dbId AS peDbId, " +
                "       m.displayName AS displayName, " +
                "       m.schemaClass AS schemaClass, " +
                "       COLLECT(DISTINCT({ " +
                "              dbId: re.dbId, " +
                "              displayName: re.displayName, " +
                "              identifier: CASE WHEN re.variantIdentifier IS NOT NULL THEN re.variantIdentifier ELSE re.identifier END, " +
                "              url: re.url, " +
                "              schemaClass: re.schemaClass, " +
                "              icon: icon " +
                "       })) AS refEntities";

        return neo4jClient.query(query).in(databaseName).bindAll(Collections.singletonMap("stId", stId)).fetchAs(Participant.class).mappedBy( (t, record) -> Participant.build(record)).all();
    }

}
