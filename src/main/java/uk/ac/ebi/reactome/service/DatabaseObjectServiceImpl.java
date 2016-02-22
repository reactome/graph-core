package uk.ac.ebi.reactome.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.domain.result.Participant;
import uk.ac.ebi.reactome.domain.result.Participant2;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;

import java.util.Collection;

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
    public DatabaseObject findById(String id) {
        id = id.trim().split("\\.")[0];
        if (id.startsWith("R")) {
            return databaseObjectRepository.findByStableIdentifier(id);
        } else if (StringUtils.isNumeric(id)){
            return databaseObjectRepository.findByDbId(Long.parseLong(id));
        } else {
            return null;
        }
    }

    @Override
    public GraphRepository<DatabaseObject> getRepository() {
        return databaseObjectRepository;
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
    public Collection<Participant2> getParticipatingMolecules3(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules3(dbId);
    }

    @Override
    public Collection<LabelsCount> getLabelsCount() {
        return databaseObjectRepository.getLabelsCount();
    }

}
