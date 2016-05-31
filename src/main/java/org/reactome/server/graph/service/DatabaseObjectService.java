package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public class DatabaseObjectService {

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @SuppressWarnings("unused")
    public DatabaseObject findById(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbId(Long.parseLong(id));
        }
        return null;
    }

    @SuppressWarnings("unused")
    public DatabaseObject findByIdNoRelations(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStIdNoRelations(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbIdNoRelations(Long.parseLong(id));
        }
        return null;
    }

    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);
    }

    public DatabaseObject findByStId(String stId) {
        return databaseObjectRepository.findByStId(stId);
    }

    public DatabaseObject findByDbIdNoRelations(Long dbId) {
        return databaseObjectRepository.findByDbIdNoRelations(dbId);
    }

    public DatabaseObject findByStIdRelations(String stId) {
        return databaseObjectRepository.findByStIdNoRelations(stId);
    }

    public Collection<DatabaseObject> findByDbIdsNoRelations(Collection<Long> dbIds) {
        return databaseObjectRepository.findByDbIdsNoRelations(dbIds);
    }

    public Collection<DatabaseObject> findByStIdsNoRelations(Collection<String> stIds) {
        return databaseObjectRepository.findByStIdsNoRelations(stIds);
    }

}
