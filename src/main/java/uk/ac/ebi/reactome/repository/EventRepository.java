package uk.ac.ebi.reactome.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import uk.ac.ebi.reactome.domain.model.Event;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
public interface EventRepository extends GraphRepository<Event> {

    Event findByDbId(Long dbId);
    Event findByStableIdentifier(String stableIdentifier);

}