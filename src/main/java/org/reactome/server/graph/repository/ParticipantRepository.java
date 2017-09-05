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

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})-[:hasEvent|input|output|catalystActivity*]->(m)-[:physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(x:ReferenceEntity) " +
            "RETURN m.dbId AS peDbId, m.displayName AS displayName, m.simpleLabel AS schemaClass, COLLECT(DISTINCT({dbId: x.dbId, displayName: x.displayName, identifier:x.identifier, url:x.url, schemaClass:x.simpleLabel})) AS refEntities")
    Collection<Participant> getParticipants(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})-[:hasEvent|input|output|catalystActivity*]->(m)-[:physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(x:ReferenceEntity) " +
            "RETURN m.dbId AS peDbId, m.displayName AS displayName, m.simpleLabel AS schemaClass, COLLECT(DISTINCT({dbId: x.dbId, displayName: x.displayName, identifier:x.identifier, url:x.url, schemaClass:x.simpleLabel})) AS refEntities")
    Collection<Participant> getParticipants(String stId);
}
