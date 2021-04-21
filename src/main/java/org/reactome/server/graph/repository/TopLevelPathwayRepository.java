package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TopLevelPathwayRepository extends Neo4jRepository<TopLevelPathway, Long> {

    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n ORDER BY toLower(n.displayName)")
    Collection<TopLevelPathway> getTopLevelPathways();

    @Query("MATCH (n:TopLevelPathway)-[r:species]-(s:Species{displayName:$speciesName}) RETURN n, collect(r), collect(s) ORDER BY toLower(n.displayName) ASC")
    Collection<TopLevelPathway> getTopLevelPathwaysByName(@Param("speciesName") String speciesName);

    @Query("MATCH (n:TopLevelPathway)-[r:species]-(s:Species{taxId:$taxId}) RETURN n, collect(r), collect(s) ORDER BY toLower(n.displayName) ASC")
    Collection<TopLevelPathway> getTopLevelPathwaysByTaxId(@Param("taxId") String taxId);

    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n ORDER BY toLower(n.displayName)")
    Collection<TopLevelPathway> getCuratedTopLevelPathways();

    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[r:species]-(s:Species{displayName:$speciesName}) RETURN n, collect(r), collect(s) ORDER BY toLower(n.displayName)")
    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByName(@Param("speciesName") String speciesName);

    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[r:species]-(s:Species{taxId:$taxId}) RETURN n, collect(r), collect(s)  ORDER BY toLower(n.displayName)")
    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByTaxId(@Param("taxId") String taxId);
}
