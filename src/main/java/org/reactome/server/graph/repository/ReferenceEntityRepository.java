//package org.reactome.server.graph.repository;
//
//import org.reactome.server.graph.domain.model.ReferenceEntity;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//
///**

// */
//@Repository
//public interface ReferenceEntityRepository extends Neo4jRepository<ReferenceEntity> {
//
//
//    @Query(" MATCH (rd:ReferenceDatabase)<--(n{identifier:{0}})<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
//            "WITH DISTINCT pe " +
//            "MATCH (pe)-[:referenceEntity]->(n:ReferenceEntity)" +
//            "RETURN DISTINCT n")
//    Collection<ReferenceEntity> getReferenceEntitiesFor(String identifier);
//}
