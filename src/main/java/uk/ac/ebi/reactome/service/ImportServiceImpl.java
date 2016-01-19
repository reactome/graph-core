package uk.ac.ebi.reactome.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;
import uk.ac.ebi.reactome.repository.GenericRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 *
 *
 * Transactions are handled in Reacotme Importer ...
 */
@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger logger = Logger.getLogger(ImportService.class);

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    private GenericRepository genericRepository;

//    /**
//     * Is here twice to avoid logging
//     * @param dbId
//     * @return
//     */
//    @Override
//    public DatabaseObject findByDbId(Long dbId) {
//        return databaseObjectRepository.findByDbId(dbId);
//    }
//

    /**
     * @param databaseObject
     * @return
     */
    @Override
    public DatabaseObject getOrCreate(DatabaseObject databaseObject) {
        DatabaseObject oldDatabaseObject = databaseObjectRepository.findByDbId(databaseObject.getDbId());
        if (oldDatabaseObject == null) {
            return databaseObjectRepository.save(databaseObject, 0);
        }
        return databaseObject;
    }

    @Override
    public void addRelationship(Long dbIdA, Long dbIdB, String relationshipName){
        if (!genericRepository.addRelationship(dbIdA, dbIdB, relationshipName)) {
            logger.error("Adding " + relationshipName + " Relationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    /*
    Constraints & Indexing
     */
    @Override
    public void createConstraintOnDatabaseObjectDbId(){
        databaseObjectRepository.createConstraintOnDatabaseObjectDbId();
        logger.info("Added Constraint: DatabaseObject.dbId is UNIQUE");
    }

    @Override
    public void createConstraintOnDatabaseObjectStId(){
        databaseObjectRepository.createConstraintOnDatabaseObjectStId();
        logger.info("Added Constraint: DatabaseObject.stableIdentifier is UNIQUE");
    }

    @Override
    public void cleanDatabase() {
        genericRepository.cleanDatabase();;
        logger.info("GraphDatabase has been cleaned");
    }

}
