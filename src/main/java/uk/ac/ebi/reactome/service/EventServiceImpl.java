package uk.ac.ebi.reactome.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.domain.model.NegativeRegulation;
import uk.ac.ebi.reactome.domain.model.PositiveRegulation;
import uk.ac.ebi.reactome.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
public class EventServiceImpl extends ServiceImpl<Event> implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public GraphRepository<Event> getRepository() {
        return eventRepository;
    }

    @Override
    public Event findById(String id) {
        id = id.trim().split("\\.")[0];
        if (id.startsWith("R")) {
            return eventRepository.findByStableIdentifier(id);
        } else if (StringUtils.isNumeric(id)){
            return eventRepository.findByDbId(Long.parseLong(id));
        } else {
            return null;
        }
    }

    @Override
    public Event findByDbId(Long dbId) {
        return eventRepository.findByDbId(dbId);
    }

    @Override
    public Event findByStableIdentifier(String stableIdentifier) {
        return eventRepository.findByStableIdentifier(stableIdentifier);
    }

    @Override
    @Transactional
    public Event findByIdWithLegacyFields(String id) {
        Event event = findById(id);
        if (event.getNegativelyRegulatedBy() != null) {
            List<DatabaseObject> regulator = new ArrayList<>();
            for (NegativeRegulation negativeRegulation : event.getNegativelyRegulatedBy()) {
                eventRepository.findOne(negativeRegulation.getId());
                regulator.add(negativeRegulation.getRegulator());
            }
            event.setNegativeRegulators(regulator);
        }
        if (event.getPositivelyRegulatedBy() != null) {
            List<DatabaseObject> regulator = new ArrayList<>();
            for (PositiveRegulation positiveRegulation : event.getPositivelyRegulatedBy()) {
                eventRepository.findOne(positiveRegulation.getId());
                regulator.add(positiveRegulation.getRegulator());
            }
            event.setPositiveRegulators(regulator);
        }
        if (event.getOrthologousEvent() != null) {
            Set<Event> orthologousEvents = new HashSet<>();
            for (Event orthologousEvent : event.getInferredTo()) {
                orthologousEvents.add(orthologousEvent);
            }
            event.setOrthologousEvent(orthologousEvents);
        }
        return event;
    }
}
