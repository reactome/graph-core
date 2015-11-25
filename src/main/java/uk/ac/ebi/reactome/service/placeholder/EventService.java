package uk.ac.ebi.reactome.service.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.repository.placeholder.EventRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public  class EventService {

//    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private EventRepository eventRepository;

    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findByDbId(Long dbId) {
        return eventRepository.findByDbId(dbId);
    }

    public Event findByStId(String stId) {
        return eventRepository.findByStId(stId);
    }

    public Event getOrCreate(Event event) {
        Event oldEvent = eventRepository.findByDbId(event.getDbId());
        if (oldEvent == null) {
            return eventRepository.save(event, 0);
        }
        return event;
    }
}



//private static EventRepository eventRepository;
//
//    public static Iterable<Event> findAll() {
//        return eventRepository.findAll();
//    }
//@Autowired
//public void setEventRepository(EventRepository eventRepository){
//    this.eventRepository = eventRepository
//}