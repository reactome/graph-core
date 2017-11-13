package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface PhysicalEntityRepository extends GraphRepository<PhysicalEntity> {

    @Query("MATCH (n:PhysicalEntity{dbId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) WHERE NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(Long dbId);

    @Query("MATCH (n:PhysicalEntity{stId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) WHERE NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(String stId);

    @Query(" MATCH (rd:ReferenceDatabase)<-[:referenceDatabase]-(n{identifier:{0}}) " +
            "WHERE rd.displayName =~ {1} " +
            "WITH DISTINCT n " +
            "MATCH (n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "MATCH (c:Complex)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT c")
    Collection<Complex> getComplexesFor(String identifier, String resource);

    @Query("MATCH (:PhysicalEntity{dbId:{0}})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunits(Long dbId);

    @Query("MATCH (:PhysicalEntity{stId:{0}})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunits(String stId);

    @Query("MATCH (:PhysicalEntity{dbId:{0}})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunitsNoStructures(Long dbId);

    @Query("MATCH (:PhysicalEntity{stId:{0}})-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getPhysicalEntitySubunitsNoStructures(String stId);
}