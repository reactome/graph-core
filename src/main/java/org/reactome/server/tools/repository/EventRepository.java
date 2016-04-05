package org.reactome.server.tools.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import org.reactome.server.tools.domain.model.Event;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@Repository
public interface EventRepository extends GraphRepository<Event> {

    Event findByDbId(Long dbId);
    Event findByStableIdentifier(String stableIdentifier);

}