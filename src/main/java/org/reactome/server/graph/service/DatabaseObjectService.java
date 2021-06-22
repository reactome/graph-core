package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger
 * @author Guilherme Viteri
 */
@Service
public class DatabaseObjectService {

    private final DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    public DatabaseObjectService(DatabaseObjectRepository databaseObjectRepository) {
        this.databaseObjectRepository = databaseObjectRepository;
    }

    public <T extends DatabaseObject> T findById(Object identifier) {
        return ServiceUtils.fetchById(identifier, true, databaseObjectRepository::findByStId, databaseObjectRepository::findByDbId);
    }

    public <T extends DatabaseObject> T findByIdNoRelations(Object identifier) {
        return ServiceUtils.fetchById(identifier, false, databaseObjectRepository::findByStIdNoRelations, databaseObjectRepository::findByDbIdNoRelations);
    }

    public <T extends DatabaseObject> Collection<T> findByIdsNoRelations(Collection<?> identifiers) {
        Set<Long> dbIds = new HashSet<>();
        Set<String> stIds = new HashSet<>();
        for (Object identifier : identifiers) {
            String id = DatabaseObjectUtils.getIdentifier(identifier);
            if (DatabaseObjectUtils.isStId(id)) {
                stIds.add(id);
            } else if (DatabaseObjectUtils.isDbId(id)) {
                dbIds.add(Long.parseLong(id));
            }
        }
        if (dbIds.isEmpty() && stIds.isEmpty()) return null;
        Collection<T> databaseObjects = new HashSet<>();
        if (!dbIds.isEmpty()) databaseObjects.addAll(databaseObjectRepository.findByDbIdsNoRelations(dbIds));
        if (!stIds.isEmpty()) databaseObjects.addAll(databaseObjectRepository.findByStIdsNoRelations(stIds));
        return databaseObjects;
    }
}
