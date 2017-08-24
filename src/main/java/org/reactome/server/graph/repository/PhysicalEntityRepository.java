package org.reactome.server.graph.repository;

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

    @Query("Match (n:PhysicalEntity{dbId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(Long dbId);

    @Query("Match (n:PhysicalEntity{stId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(String stId);

    @Query("MATCH (:Complex{dbId:{0}})-[:hasComponent|hasMember|hasCandidate*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getComplexSubunits(Long dbId);

    @Query("MATCH (:Complex{stId:{0}})-[:hasComponent|hasMember|hasCandidate*]->(pe:PhysicalEntity) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getComplexSubunits(String stId);

    @Query("MATCH (:Complex{dbId:{0}})-[:hasComponent|hasMember|hasCandidate*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getComplexSubunitsNoStructures(Long dbId);

    @Query("MATCH (:Complex{stId:{0}})-[:hasComponent|hasMember|hasCandidate*]->(pe:PhysicalEntity) WHERE NOT (pe:Complex) AND NOT(pe:EntitySet) RETURN DISTINCT pe")
    Collection<PhysicalEntity> getComplexSubunitsNoStructures(String stId);
}