//package org.reactome.server.graph.repository;
//
//import org.reactome.server.graph.domain.model.TopLevelPathway;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//
///**
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @author Antonio Fabregat (fabregat@ebi.ac.uk)
// */
//@Repository
//public interface TopLevelPathwayRepository extends Neo4jRepository<TopLevelPathway, Long> {
//
//    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n ORDER BY LOWER(n.displayName)")
//    Collection<TopLevelPathway> getTopLevelPathways();
//
//    @Query("MATCH (n:TopLevelPathway)-[:species]-(s:Species{displayName:{0}}) RETURN n ORDER BY LOWER(n.displayName) ASC")
//    Collection<TopLevelPathway> getTopLevelPathwaysByName(String speciesName);
//
//    @Query("MATCH (n:TopLevelPathway)-[:species]-(:Species{taxId:{0}}) RETURN n ORDER BY LOWER(n.displayName) ASC")
//    Collection<TopLevelPathway> getTopLevelPathwaysByTaxId(String taxId);
//
//    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n ORDER BY LOWER(n.displayName)")
//    Collection<TopLevelPathway> getCuratedTopLevelPathways();
//
//    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[:species]-(:Species{displayName:{0}}) RETURN n ORDER BY LOWER(n.displayName)")
//    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByName(String speciesName);
//
//    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[:species]-(:Species{taxId:{0}}) RETURN n ORDER BY LOWER(n.displayName)")
//    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByTaxId(String taxId);
//}
