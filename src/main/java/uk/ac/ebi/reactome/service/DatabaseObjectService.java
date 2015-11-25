package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface DatabaseObjectService extends Service<DatabaseObject> {

    DatabaseObject findOne(Long id, int depth);

    DatabaseObject findByDbId(Long dbId);

    DatabaseObject findByStId(String stId);
}
