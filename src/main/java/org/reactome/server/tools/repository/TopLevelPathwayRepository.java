package org.reactome.server.tools.repository;

import org.reactome.server.tools.domain.model.TopLevelPathway;
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

    @Query("Match (n:TopLevelPathway) RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways();

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where s.dbId = {speciesId}  RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways(Long speciesId);

    @Query("Match (n:TopLevelPathway)-[:species]-(s) Where s.displayName = {speciesName}  RETURN n")
    Collection<TopLevelPathway> getTopLevelPathways(String speciesName);
}
