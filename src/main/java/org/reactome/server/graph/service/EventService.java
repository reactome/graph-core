package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.repository.EventRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GeneralService generalService;

    public Event findById(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return eventRepository.findByStableIdentifier(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return eventRepository.findByDbId(Long.parseLong(id));
        }
        return null;
    }

    public Event findByDbId(Long dbId) {
        return eventRepository.findByDbId(dbId);
    }

    public Event findByStableIdentifier(String stableIdentifier) {
        return eventRepository.findByStableIdentifier(stableIdentifier);
    }

    @Deprecated
    @Transactional
    public Event findByIdWithLegacyFields(String id) {
        Event event = findById(id);
        event = addRegulators(event);
        return event;
    }

    @Deprecated
    public ReactionLikeEvent loadCatalysts (ReactionLikeEvent reactionLikeEvent) {

        if (reactionLikeEvent == null) return null;
        if (reactionLikeEvent.getCatalystActivity() != null ) {
            for (CatalystActivity catalystActivity : reactionLikeEvent.getCatalystActivity()) {
                eventRepository.findOne(catalystActivity.getId());
            }
        }
        return reactionLikeEvent;
    }

    @Deprecated
    public Event addRegulators (Event event) {
        if (event == null) return null;
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
        return event;
    }

//    @Deprecated
//    public Event addOnlyRegulators (Event event) {
//
//        if (event == null) return null;
//        List<Regulation> regulations = event.getRegulations();
//        if (regulations != null && !regulations.isEmpty()) {
//            List<Long> dbIds = new ArrayList<>();
//            for (Regulation regulation : regulations) {
//                dbIds.add(regulation.getDbId());
//            }
//            generalService.findByDbIds(dbIds, RelationshipDirection.OUTGOING, "regulator");
//
//            List<DatabaseObject> regulator = new ArrayList<>();
//            if (event.getNegativelyRegulatedBy() != null) {
//                for (NegativeRegulation negativeRegulation : event.getNegativelyRegulatedBy()) {
//                    regulator.add(negativeRegulation.getRegulator());
//                }
//                event.setNegativeRegulators(regulator);
//            }
//            if (event.getPositivelyRegulatedBy() != null) {
//                for (PositiveRegulation positiveRegulation : event.getPositivelyRegulatedBy()) {
//                    regulator.add(positiveRegulation.getRegulator());
//                }
//                event.setPositiveRegulators(regulator);
//            }
//        }
//        return event;
//    }

}
