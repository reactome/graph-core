package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReferenceEntityRepository extends Neo4jRepository<ReferenceEntity, Long> {

    @Query(" MATCH (rd:ReferenceDatabase)<--(n{identifier:$identifier})<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "MATCH (pe)-[:referenceEntity]->(n:ReferenceEntity)" +
            "RETURN DISTINCT n")
    Collection<ReferenceEntity> getReferenceEntitiesFor(@Param("identifier") String identifier);

    @Query(" MATCH (n:DatabaseObject{dbId:$dbId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(@Param("dbId") Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:$stId})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(m:ReferenceEntity) " +
            "RETURN DISTINCT m")
    Collection<ReferenceEntity> getParticipatingReferenceEntities(@Param("stId") String stId);

}
