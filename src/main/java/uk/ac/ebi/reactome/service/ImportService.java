package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 */
public interface ImportService {

//    DatabaseObject findByDbId(Long dbId);
    DatabaseObject getOrCreate(DatabaseObject databaseObject);
    void addRelationship(Long dbIdA, Long dbIdB, String relationshipName);

    void cleanDatabase();
    /* ------------------------------ Constraints & Indexing ------------------------------ */

    void createConstraintOnDatabaseObjectDbId();

    void createConstraintOnDatabaseObjectStId();
}