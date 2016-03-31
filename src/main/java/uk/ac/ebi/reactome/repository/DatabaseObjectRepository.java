package uk.ac.ebi.reactome.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.domain.result.Participant;

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

//    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity*]->(m)-[:activeUnit|physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(x:ReferenceEntity) RETURN m.dbId AS ewasDbId, m.displayName AS ewasName, COLLECT(DISTINCT x.dbId) AS refEntitiesDbIds, COLLECT(DISTINCT x.displayName) AS refEntitiesNames")
//    Collection<Participant> getParticipatingMolecules2(Long dbId);

    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity*]->(m)-[:activeUnit|physicalEntity|hasMember|hasComponent|hasCandidate|repeatedUnit|referenceEntity*]->(x:ReferenceEntity) RETURN m.dbId AS peDbId, m.displayName AS displayName, COLLECT(DISTINCT({dbId: x.dbId, name: x.displayName, identifier:x.identifier, url:x.url})) AS refEntities")
    Collection<Participant> getParticipatingMolecules3(Long dbId);

    @Query("MATCH (n:Event{dbId:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingMolecules4(Long dbId);

    @Query("MATCH (n:Event{stableIdentifier:{0}})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(m:PhysicalEntity) RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingMolecules4(String stId);

    @Query("MATCH (n) WHERE NOT (n:TopLevelPathway)  RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count")
    Collection<LabelsCount> getLabelsCount();

    @Query("Match (n:SimpleEntity)-[:referenceEntity]-(m:ReferenceEntity) RETURN Distinct(m)")
    Collection<ReferenceEntity> getAllChemicals();

}
