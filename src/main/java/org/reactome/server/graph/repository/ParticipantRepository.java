package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.result.Participant;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface ParticipantRepository extends GraphRepository<PhysicalEntity> {

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|regulatedBy|regulator*]->(m)-[:physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(re:ReferenceEntity) " +
            "RETURN m.dbId AS peDbId, " +
            "       m.displayName AS displayName, " +
            "       m.schemaClass AS schemaClass, " +
            "       COLLECT(DISTINCT({" +
            "              dbId: re.dbId, " +
            "              displayName: re.displayName, " +
            "              identifier: CASE WHEN re.variantIdentifier IS NOT NULL THEN re.variantIdentifier ELSE re.identifier END, " +
            "              url: re.url, " +
            "              schemaClass: re.schemaClass" +
            "       })) AS refEntities")
    Collection<Participant> getParticipants(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|regulatedBy|regulator*]->(m)-[:physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(re:ReferenceEntity) " +
            "RETURN m.dbId AS peDbId, " +
            "       m.displayName AS displayName, " +
            "       m.schemaClass AS schemaClass, " +
            "       COLLECT(DISTINCT({" +
            "              dbId: re.dbId, " +
            "              displayName: re.displayName, " +
            "              identifier: CASE WHEN re.variantIdentifier IS NOT NULL THEN re.variantIdentifier ELSE re.identifier END, " +
            "              url: re.url, " +
            "              schemaClass: re.schemaClass" +
            "       })) AS refEntities")
    Collection<Participant> getParticipants(String stId);
}
