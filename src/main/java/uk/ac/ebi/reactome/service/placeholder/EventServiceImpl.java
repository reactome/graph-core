package uk.ac.ebi.reactome.service.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.repository.placeholder.EventRepository;
import uk.ac.ebi.reactome.service.ServiceImpl;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public class EventServiceImpl extends ServiceImpl<Event> implements EventServic {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public GraphRepository<Event> getRepository() {
        return eventRepository;
    }
}
