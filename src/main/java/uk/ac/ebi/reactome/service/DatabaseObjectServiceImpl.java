package uk.ac.ebi.reactome.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public class DatabaseObjectServiceImpl extends ServiceImpl<DatabaseObject> implements DatabaseObjectService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectServiceImpl.class);

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Override
    public GraphRepository<DatabaseObject> getRepository() {
        return databaseObjectRepository;
    }

    @Override
    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);
    }

    @Override
    public DatabaseObject findByStId(String stId) {
        return databaseObjectRepository.findByStId(stId);
    }

    @Transactional
    public DatabaseObject getOrCreate(DatabaseObject databaseObject) {
        DatabaseObject oldDatabaseObject = databaseObjectRepository.findByDbId(databaseObject.getDbId());
        if (oldDatabaseObject == null) {
            return databaseObjectRepository.save(databaseObject, 0);
        }
        return databaseObject;
    }

    /*
    Methods for adding relationships to two entities by their dbIds.
     */
    @Override
    public void createInputRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createInputRelationship(dbIdA, dbIdB)) {
            logger.error("Adding InputRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createOutputRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createOutputRelationship(dbIdA, dbIdB)) {
            logger.error("Adding OutputRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createCatalystRelationship(Long dbIdA, Long dbIdB) {
        if (!databaseObjectRepository.createCatalystRelationship(dbIdA, dbIdB)) {
            logger.error("Adding CatalystRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createCandidateRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createCandidateRelationship(dbIdA, dbIdB)) {
            logger.error("Adding CandidateRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createComponentRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createComponentRelationship(dbIdA, dbIdB)) {
            logger.error("Adding ComponentRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createMemberRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createMemberRelationship(dbIdA, dbIdB)) {
            logger.error("Adding MemberRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createEventRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createEventRelationship(dbIdA, dbIdB)) {
            logger.error("Adding EventRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createRepeatedUnitRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createRepeatedUnitRelationship(dbIdA, dbIdB)) {
            logger.error("Adding RepeatedUnitRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
        }
    }

    @Override
    public void createReferenceEntityRelationship(Long dbIdA, Long dbIdB){
        if (!databaseObjectRepository.createReferenceEntityRelationship(dbIdA, dbIdB)) {
            logger.error("Adding ReferenceEntityRelationship between entry with dbId: " + dbIdA + " and dbId: " + dbIdB + " failed");
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
        logger.info("Added Constraint: DatabaseObject.stId is UNIQUE");
    }


}
