package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface GenericService {

    DatabaseObject findByDbIdWithSession(Long dbId);
    DatabaseObject findByDbIdWithSession(Long dbId, Integer depth);
    DatabaseObject findStIdWithSession(String stId);
    DatabaseObject findStIdWithSession(String stId, Integer depth);

    void cleanDatabase();
}
