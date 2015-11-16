package uk.ac.ebi.reactome.service;

import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.service.Service;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface DatabaseObjectService extends Service<DatabaseObject> {


    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByStId(String stId);

    DatabaseObject getOrCreate(DatabaseObject databaseObject);

    /*
    Methods for adding relationships to two entities by their dbIds.
     */
    void createInputRelationship(Long dbIdA, Long dbIdB);
    void createOutputRelationship(Long dbIdA, Long dbIdB);
    void createCatalystRelationship(Long dbIdA, Long dbIdB);
    void createCandidateRelationship(Long dbIdA, Long dbIdB);
    void createComponentRelationship(Long dbIdA, Long dbIdB);
    void createMemberRelationship(Long dbIdA, Long dbIdB);
    void createEventRelationship(Long dbIdA, Long dbIdB);
    void createRepeatedUnitRelationship(Long dbIdA, Long dbIdB);
    void createReferenceEntityRelationship(Long dbIdA, Long dbIdB);

    void createConstraintOnDatabaseObjectDbId();
    void createConstraintOnDatabaseObjectStId();
}
