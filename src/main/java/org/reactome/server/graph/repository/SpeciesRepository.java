//package org.reactome.server.graph.repository;
//
//import org.reactome.server.graph.domain.model.Species;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author Antonio Fabregat <fabregat@ebi.ac.uk>
// */
//@Repository
//public interface SpeciesRepository extends Neo4jRepository<Species, Long> {
//
//    @Query("MATCH (n:Species)<-[:species]-(:TopLevelPathway) RETURN n ORDER BY n.displayName")
//    List<Species> getSpecies();
//
//    @Query("MATCH (n:Species) RETURN n ORDER BY n.displayName")
//    List<Species> getAllSpecies();
//
//    @Query("MATCH (n:Species{taxId:{0}}) RETURN n")
//    Species getSpeciesByTaxId(String taxId);
//
//    @Query("MATCH (n:Species{dbId:{0}}) RETURN n")
//    Species getSpeciesByDbId(Long dbId);
//
//    @Query("MATCH (n:Species) WHERE {0} IN n.name RETURN n")
//    Species getSpeciesByName(String name);
//}
