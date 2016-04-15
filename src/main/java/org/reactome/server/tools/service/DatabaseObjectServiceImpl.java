package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.result.LabelsCount;
import org.reactome.server.tools.domain.result.Participant;
import org.reactome.server.tools.repository.DatabaseObjectRepository;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public class DatabaseObjectServiceImpl extends ServiceImpl<DatabaseObject> implements DatabaseObjectService {

//    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectServiceImpl.class);

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Override
    public GraphRepository<DatabaseObject> getRepository() {
        return databaseObjectRepository;
    }

    @Override
    public DatabaseObject findById(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStableIdentifier(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbId(Long.parseLong(id));
        }
        return null;
    }

    @Override
    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);
    }

    @Override
    public DatabaseObject findByDbIdNoRelations(Long dbId) {
        return databaseObjectRepository.findByDbIdNoRelations(dbId);
    }

    @Override
    public DatabaseObject findByStableIdentifier(String stableIdentifier) {
        return databaseObjectRepository.findByStableIdentifier(stableIdentifier);
    }

    @Override
    public Collection<ReferenceEntity> getParticipatingMolecules(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules(dbId);
    }

    @Override
    public Collection<Participant> getParticipatingMolecules2(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules2(dbId);
    }

    @Override
    public Collection<PhysicalEntity> getParticipatingMolecules3(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.getParticipatingMolecules3(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.getParticipatingMolecules3(Long.parseLong(id));
        }
        return null;
    }

    @Override
    public Collection<LabelsCount> getLabelsCount() {
        return databaseObjectRepository.getLabelsCount();
    }

    public Collection<PhysicalEntity> getOtherFormsOfThisMolecule(Long dbId) {
        return databaseObjectRepository.getOtherFormsOfThisMolecule(dbId);
    }

}
