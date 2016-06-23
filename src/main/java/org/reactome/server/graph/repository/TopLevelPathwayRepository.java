package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface TopLevelPathwayRepository extends GraphRepository<TopLevelPathway> {

    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getTopLevelPathways();

    @Query("MATCH (n:TopLevelPathway)-[:species]-(s:Species{displayName:{0}}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getTopLevelPathwaysByName(String speciesName);

    @Query("MATCH (n:TopLevelPathway)-[:species]-(:Species{taxId:{0}}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getTopLevelPathwaysByTaxId(String taxId);

    @Query("MATCH (n:TopLevelPathway{isInferred:False}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getCuratedTopLevelPathways();

    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[:species]-(:Species{displayName:{0}}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByName(String speciesName);

    @Query("MATCH (n:TopLevelPathway{isInferred:False})-[:species]-(:Species{taxId:{0}}) RETURN n Order By n.displayName")
    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByTaxId(String taxId);
}
