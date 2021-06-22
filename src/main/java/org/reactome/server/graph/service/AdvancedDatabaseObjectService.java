package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @since 05.06.16.
 */
@Service
public class AdvancedDatabaseObjectService {

    private final AdvancedDatabaseObjectRepository advancedDatabaseObjectRepository;

    @Autowired
    public AdvancedDatabaseObjectService(AdvancedDatabaseObjectRepository repository) {
        this.advancedDatabaseObjectRepository = repository;
    }

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public <T extends DatabaseObject> T findEnhancedObjectById(Object identifier) {
        return ServiceUtils.fetchById(identifier, true, advancedDatabaseObjectRepository::findEnhancedObjectById, advancedDatabaseObjectRepository::findEnhancedObjectById);
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findById(Object identifier, Integer limit) {
        return ServiceUtils.fetchById(identifier, limit, true, advancedDatabaseObjectRepository::findById, advancedDatabaseObjectRepository::findById);
    }

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    @Deprecated
    public <T extends DatabaseObject> DatabaseObject findByProperty(Class<T> clazz, String property, Object value) {
        return advancedDatabaseObjectRepository.findByProperty(clazz, property, value);
    }

    @Deprecated
    public <T extends DatabaseObject> Collection<DatabaseObject> findAllByProperty(Class<T> clazz, String property, Object value) {
        return advancedDatabaseObjectRepository.findAllByProperty(clazz, property, value);
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public <T extends DatabaseObject> T findById(Object identifier, RelationshipDirection direction) {
        return ServiceUtils.fetchById(identifier, direction, true, advancedDatabaseObjectRepository::findById, advancedDatabaseObjectRepository::findById);
    }

    public <T extends DatabaseObject> T findById(Object identifier, RelationshipDirection direction, String... relationships) {
        return ServiceUtils.fetchById(identifier, direction, Arrays.asList(relationships), true, advancedDatabaseObjectRepository::findById, advancedDatabaseObjectRepository::findById);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByDbIds(dbIds, direction, Arrays.asList(relationships));
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByStIds(stIds, direction, Arrays.asList(relationships));
    }

    public Collection<DatabaseObject> findByIds(Collection<Object> ids, RelationshipDirection direction) {
        Collection<DatabaseObject> rtn = new HashSet<>();
        for (Object id : ids) {
            DatabaseObject aux = findById(id, direction);
            if (aux != null) rtn.add(aux);
        }
        return rtn;
    }

    public Collection<DatabaseObject> findByIds(Collection<Object> ids, RelationshipDirection direction, String... relationships) {
        Collection<DatabaseObject> rtn = new HashSet<>();
        for (Object id : ids) {
            DatabaseObject aux = findById(id, direction, relationships);
            if (aux != null) rtn.add(aux);
        }
        return rtn;
    }

    public Collection<DatabaseObject> findCollectionByRelationship(Long dbId, String clazz, Class<?> collectionClazz, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findCollectionByRelationship(dbId, clazz, collectionClazz, direction, Arrays.asList(relationships));
    }

    public <T extends DatabaseObject> T findByRelationship(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByRelationship(dbId, clazz, direction, Arrays.asList(relationships));
    }

    // ----------------------------------------- Custom Query Methods --------------------------------------------------

    public void customQuery(String query) {
        customQuery(query, Collections.emptyMap());
    }

    public void customQuery(String query, Map<String, Object> parameters) {
        advancedDatabaseObjectRepository.customQuery(query, parameters);
    }

    public <T> T getCustomQueryResult(Class<T> clazz, String query) throws CustomQueryException {
        return getCustomQueryResult(clazz, query, Collections.emptyMap());
    }

    public <T> T getCustomQueryResult(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryResult(clazz, query, parameters);
    }

    public <T> Collection<T> getCustomQueryResults(Class<T> clazz, String query) throws CustomQueryException {
        return getCustomQueryResults(clazz, query, Collections.emptyMap());
    }

    public <T> Collection<T> getCustomQueryResults(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryResults(clazz, query, parameters);
    }
}
