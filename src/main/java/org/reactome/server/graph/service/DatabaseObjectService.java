package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
@SuppressWarnings("WeakerAccess")
public class DatabaseObjectService {

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    public DatabaseObject findById(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbId(Long.parseLong(id));
        }
        return null;
    }

    public DatabaseObject findByIdNoRelations(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return databaseObjectRepository.findByStIdNoRelations(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return databaseObjectRepository.findByDbIdNoRelations(Long.parseLong(id));
        }
        return null;
    }

    public Collection<DatabaseObject> findByIdsNoRelations(Collection<?> identifiers) {
        Set<Long> dbIds = new HashSet<>();
        Set<String> stIds = new HashSet<>();
        for (Object identifier : identifiers) {
            String id = DatabaseObjectUtils.getIdentifier(identifier);
            if (DatabaseObjectUtils.isStId(id)) {
                stIds.add(id);
            } else if (DatabaseObjectUtils.isDbId(id)){
                dbIds.add(Long.parseLong(id));
            }
        }
        if (dbIds.isEmpty() && stIds.isEmpty()) return null;
        Collection<DatabaseObject> databaseObjects = new HashSet<>();
        if (!dbIds.isEmpty()) databaseObjects.addAll(databaseObjectRepository.findByDbIdsNoRelations(dbIds));
        if (!stIds.isEmpty()) databaseObjects.addAll(databaseObjectRepository.findByStIdsNoRelations(stIds));
        return databaseObjects;
    }
}
