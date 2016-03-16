package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.Event;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@SuppressWarnings("SameParameterValue")
public interface EventService extends Service<Event>  {

    Event findById(String id);
    Event findByDbId(Long dbId);
    Event findByStableIdentifier(String stableIdentifier);

    Event findByIdWithLegacyFields(String id);



}
