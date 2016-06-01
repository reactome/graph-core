package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.04.16.
 */
@Repository
public interface TopLevelPathwayRepository extends GraphRepository<TopLevelPathway> {

    @Query("Match (n:TopLevelPathway) WHERE n.isInferred = false RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways();

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where s.dbId = {0}  RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways(Long speciesId);

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where s.displayName = {0}  RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways(String speciesName);

    @Query("Match (n:TopLevelPathway)-[:species]-(:Species{taxId:{0}}) RETURN n")
    Collection<TopLevelPathway> getTopLevelPathwaysByTaxId(String taxId);

    @Query("Match (n:TopLevelPathway) Where n.isInferred = false  RETURN n")
    Collection<TopLevelPathway> getCuratedTopLevelPathways();

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where n.isInferred = false AND s.dbId = {0}  RETURN n")
    Collection<TopLevelPathway> getCuratedTopLevelPathways(Long speciesId);

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where n.isInferred = false AND s.displayName = {0}  RETURN n")
    Collection<TopLevelPathway> getCuratedTopLevelPathways(String speciesName);

    @Query("Match (n:TopLevelPathway)-[:species]-(s)-[:crossReference]-(c:DatabaseIdentifier) WHERE n.isInferred = false AND c.identifier={0} RETURN n")
    Collection<TopLevelPathway> getCuratedTopLevelPathwaysByTaxId(String taxId);
}
