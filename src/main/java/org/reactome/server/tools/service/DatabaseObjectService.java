package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.reactome.server.tools.repository.DatabaseObjectRepository;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public class DatabaseObjectService {

//    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectServiceImpl.class);

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    public DatabaseObject findById(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStableIdentifier(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbId(Long.parseLong(id));
        }
        return null;
    }

    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);
    }

    public DatabaseObject findByStableIdentifier(String stableIdentifier) {
        return databaseObjectRepository.findByStableIdentifier(stableIdentifier);
    }

    public DatabaseObject findByDbIdNoRelations(Long dbId) {
        return databaseObjectRepository.findByDbIdNoRelations(dbId);
    }
//
//    @Override
//    public Collection<ReferenceEntity> getParticipatingMolecules(Long dbId) {
//        return databaseObjectRepository.getParticipatingMolecules(dbId);
//    }
//
//    @Override
//    public Collection<Participant> getParticipatingMolecules2(Long dbId) {
//        return databaseObjectRepository.getParticipatingMolecules2(dbId);
//    }
//
//    @Override
//    public Collection<PhysicalEntity> getParticipatingMolecules3(String id) {
//        id = DatabaseObjectUtils.trimId(id);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return databaseObjectRepository.getParticipatingMolecules3(id);
//        } else if (DatabaseObjectUtils.isDbId(id)){
//            return databaseObjectRepository.getParticipatingMolecules3(Long.parseLong(id));
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<SchemaClassCount> getLabelsCount() {
//        return databaseObjectRepository.getLabelsCount();
//    }
//
//    public Collection<PhysicalEntity> getOtherFormsOfThisMolecule(Long dbId) {
//        return databaseObjectRepository.getOtherFormsOfThisMolecule(dbId);
//    }
//
//
//
//    public Collection<ComponentOf> getComponentsOf(String stableIdentifier) {
//        return databaseObjectRepository.getComponentsOf(stableIdentifier);
//    }

}
