package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
@Repository
public interface PhysicalEntityRepository extends Neo4jRepository<PhysicalEntity, Long> {

    @Query("MATCH (n:PhysicalEntity{dbId:$dbId})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) WHERE NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(@Param("dbId") Long dbId);

    @Query("MATCH (n:PhysicalEntity{stId:$stId})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) WHERE NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(@Param("stId") String stId);

    @Query(" MATCH (rd:ReferenceDatabase)<-[:referenceDatabase]-(n{identifier:$identifier}) " +
            "WHERE rd.displayName =~ $resource " +
            "WITH DISTINCT n " +
            "MATCH (n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "MATCH (c:Complex)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT c")
    Collection<Complex> getComplexesFor(@Param("identifier") String identifier, @Param("resource") String resource);

    @Query("MATCH (:PhysicalEntity{dbId:$dbId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunits(@Param("dbId") Long dbId);

    @Query("MATCH (:PhysicalEntity{stId:$stId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunits(@Param("stId") String stId);

    @Query("MATCH (:PhysicalEntity{dbId:$dbId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunitsNoStructures(@Param("dbId") Long dbId);

    @Query("MATCH (:PhysicalEntity{stId:$stId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunitsNoStructures(@Param("$stId") String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:$dbId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(@Param("dbId") Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:$stId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(m:PhysicalEntity) " +
            "RETURN Distinct(m)")
    Collection<PhysicalEntity> getParticipatingPhysicalEntities(@Param("stId") String stId);
}