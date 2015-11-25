package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 */
public interface ImportService {

    DatabaseObject findByDbId(Long dbId);

    DatabaseObject getOrCreate(DatabaseObject databaseObject);

    /* ------------------------------ Methods for adding relationships ------------------------------ */

    void createInputRelationship(Long dbIdA, Long dbIdB);

    void createOutputRelationship(Long dbIdA, Long dbIdB);

    void createCatalystRelationship(Long dbIdA, Long dbIdB);

    void createCandidateRelationship(Long dbIdA, Long dbIdB);

    void createComponentRelationship(Long dbIdA, Long dbIdB);

    void createMemberRelationship(Long dbIdA, Long dbIdB);

    void createEventRelationship(Long dbIdA, Long dbIdB);

    void createRepeatedUnitRelationship(Long dbIdA, Long dbIdB);

    void createReferenceEntityRelationship(Long dbIdA, Long dbIdB);


    /* ------------------------------ Constraints & Indexing ------------------------------ */

    void createConstraintOnDatabaseObjectDbId();

    void createConstraintOnDatabaseObjectStId();
}