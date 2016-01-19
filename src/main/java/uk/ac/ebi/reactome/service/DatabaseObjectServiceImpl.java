package uk.ac.ebi.reactome.service;

import org.apache.log4j.Logger;
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
@Transactional(readOnly = true)
public class DatabaseObjectServiceImpl extends ServiceImpl<DatabaseObject> implements DatabaseObjectService {

private  final Logger logger = Logger.getLogger(DatabaseObjectServiceImpl.class);

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Override
    public GraphRepository<DatabaseObject> getRepository() {
        return databaseObjectRepository;
    }

    @Override
    public DatabaseObject findOne(Long id, int depth) {
        return databaseObjectRepository.findOne(id,depth);
    }

    @Override
    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);

    }
    @Override
    public DatabaseObject findByDbId2(Long dbId) {
        return databaseObjectRepository.find(dbId);

    }

    @Override
    public DatabaseObject findByDbId3(Long dbId) {
        return databaseObjectRepository.find2(dbId);

    }

    @Override
    public DatabaseObject findByStableIdentifier(String stableIdentifier) {
        return databaseObjectRepository.findByStableIdentifier(stableIdentifier);
    }
}
