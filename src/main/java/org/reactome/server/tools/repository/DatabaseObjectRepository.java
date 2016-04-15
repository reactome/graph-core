package org.reactome.server.tools.repository;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.result.LabelsCount;
import org.reactome.server.tools.domain.result.Participant;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 */
@Repository
public interface DatabaseObjectRepository extends GraphRepository<DatabaseObject>{

    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByStableIdentifier(String stableIdentifier);

    @Query("MATCH (n:DatabaseObject{dbId:{0}}) RETURN n")
    DatabaseObject findByDbIdNoRelations(Long dbId);

    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity|activeUnit|physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingMolecules(Long dbId);

    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity*]->(m)-[:activeUnit|physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(x:ReferenceEntity) RETURN m.dbId AS peDbId, m.displayName AS displayName, COLLECT(DISTINCT({dbId: x.dbId, name: x.displayName, identifier:x.identifier, url:x.url})) AS refEntities")
    Collection<Participant> getParticipatingMolecules2(Long dbId);

    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingMolecules3(Long dbId);

    @Query("MATCH (n:Event{stableIdentifier:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingMolecules3(String stId);

    @Query("MATCH (n) RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count")
    Collection<LabelsCount> getLabelsCount();

    @Query("Match (n:SimpleEntity)-[:referenceEntity]-(m:ReferenceEntity) RETURN Distinct(m)")
    Collection<ReferenceEntity> getAllChemicals();


    @Query("Match (n:PhysicalEntity{dbId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOfThisMolecule(Long dbId);


}
