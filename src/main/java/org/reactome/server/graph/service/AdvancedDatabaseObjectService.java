package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @since 05.06.16.
 */
@Service
public class AdvancedDatabaseObjectService {

    @Autowired
    private AdvancedDatabaseObjectRepository advancedDatabaseObjectRepository;

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public DatabaseObject findEnhancedObjectById(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findEnhancedObjectById(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findEnhancedObjectById(Long.parseLong(id));
        }
        return null;
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    public DatabaseObject findById(Object identifier, Integer limit) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, limit);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), limit);
        }
        return null;
    }

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return advancedDatabaseObjectRepository.findByProperty(clazz, property, value, depth);
    }

    public <T> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return advancedDatabaseObjectRepository.findAllByProperty(clazz, property, value, depth);
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public DatabaseObject findById(Object identifier, RelationshipDirection direction) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), direction);
        }
        return null;
    }

    public DatabaseObject findById(Object identifier, RelationshipDirection direction, String... relationships) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, direction, relationships);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), direction, relationships);
        }
        return null;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        return advancedDatabaseObjectRepository.findByDbIds(dbIds, direction);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction) {
        return advancedDatabaseObjectRepository.findByStIds(stIds, direction);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByDbIds(dbIds, direction, relationships);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByStIds(stIds, direction, relationships);
    }

    public Collection<DatabaseObject> findCollectionByRelationship(Long dbId, Class<?> collectionClazz, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findCollectionByRelationship(dbId, collectionClazz, direction, relationships);
    }

    public DatabaseObject findByRelationship(Long dbId, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByRelationship(dbId, direction, relationships);
    }
}
