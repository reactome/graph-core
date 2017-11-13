package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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

    public <T extends DatabaseObject> T findEnhancedObjectById(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findEnhancedObjectById(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findEnhancedObjectById(Long.parseLong(id));
        }
        return null;
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findById(Object identifier, Integer limit) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, limit);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), limit);
        }
        return null;
    }

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return advancedDatabaseObjectRepository.findByProperty(clazz, property, value, depth);
    }

    public <T extends DatabaseObject> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return advancedDatabaseObjectRepository.findAllByProperty(clazz, property, value, depth);
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public <T extends DatabaseObject> T findById(Object identifier, RelationshipDirection direction) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), direction);
        }
        return null;
    }

    public <T extends DatabaseObject> T findById(Object identifier, RelationshipDirection direction, String... relationships) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return advancedDatabaseObjectRepository.findById(id, direction, relationships);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return advancedDatabaseObjectRepository.findById(Long.parseLong(id), direction, relationships);
        }
        return null;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByDbIds(dbIds, direction, relationships);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByStIds(stIds, direction, relationships);
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
        return advancedDatabaseObjectRepository.findCollectionByRelationship(dbId, clazz, collectionClazz, direction, relationships);
    }

    public <T extends DatabaseObject> T findByRelationship(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        return advancedDatabaseObjectRepository.findByRelationship(dbId, clazz, direction, relationships);
    }

    // ----------------------------------------- Custom Query Methods --------------------------------------------------

    /**
     * Executes the specified Cypher query with the given parameters against the underlying Neo4j database and returns
     * the result automatically marshalled using Reflection and 3rd Party Library based on the given custom result.
     *
     * @param clazz         your custom object, the one that maps the Cypher Query result
     * @param query         your cypher query <code>MATCH (p:Pathway{dbId:{dbId}}) RETURN p</code>
     * @param <T>           your expected type
     * @param parametersMap cypher query parameters:
     *                      <code>
     *                      Map<String, Object> parametersMap = new HashMap<>();
     *                      parametersMap.put("dbId", 1640170);
     *                      </code>.
     *                      assign null if your does not have parameters
     * @throws CustomQueryException in case of Java Reflection related to automatic mapping or any Cypher query Runtime Exception
     */
    public <T> T customQueryForObject(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryForObject(clazz, query, parametersMap);
    }

    /**
     * Executes the specified Cypher query with the given parameters against the underlying Neo4j database and returns
     * the result automatically marshalled using Reflection and 3rd Party Library based on the given custom result.
     *
     * @param clazz         your custom object, the one that maps the Cypher Query result
     * @param query         your cypher query <code>MATCH (p:Pathway{dbId:{dbId}}) RETURN p</code>
     * @param <T>           your expected type
     * @param parametersMap cypher query parameters
     *                      <code>
     *                      Map<String, Object> parametersMap = new HashMap<>();
     *                      parametersMap.put("dbId", 1640170);
     *                      </code>.
     *                      assign null if your does not have parameters
     * @throws CustomQueryException in case of Java Reflection related to automatic mapping or any Cypher query Runtime Exception
     */
    public <T> Collection<T> customQueryForObjects(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryForObjects(clazz, query, parametersMap);
    }

    /**
     * Runs the specified Cypher query with the given parameters against the underlying Neo4j database and returns the
     * result marshalled as an object of the requested type.
     *
     * @param clazz         a class which is part of Reactome Database. All objects that extend DatabaseObject
     * @param query         your cypher query <code>MATCH (p:Pathway{dbId:{dbId}}) RETURN p</code>
     * @param parametersMap cypher query parameters:
     *                      <code>
     *                      Map<String, Object> parametersMap = new HashMap<>();
     *                      parametersMap.put("dbId", 1640170);
     *                      </code>.
     *                      assign null if your does not have parameters
     * @throws CustomQueryException in case of any Cypher query Runtime Exception
     */
    public <T extends DatabaseObject> T customQueryForDatabaseObject(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryForDatabaseObject(clazz, query, parametersMap);
    }

    /**
     * Runs the specified Cypher query with the given parameters against the underlying Neo4j database and returns the
     * result automatically as a group of objects of the requested type.
     *
     * @param clazz         a class which is part of Reactome Database. All objects that extend DatabaseObject
     * @param query         your cypher query <code>MATCH (p:Pathway{dbId:{dbId}}) RETURN p</code>
     * @param parametersMap cypher query parameters:
     *                      <code>
     *                      Map<String, Object> parametersMap = new HashMap<>();
     *                      parametersMap.put("dbId", 1640170);
     *                      </code>.
     *                      assign null if your does not have parameters
     * @throws CustomQueryException in case of any Cypher query Runtime Exception
     */
    public <T> Collection<T> customQueryForDatabaseObjects(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryForDatabaseObjects(clazz, query, parametersMap);
    }

    public <T> Collection<T> customQueryResults(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryResults(clazz, query, parametersMap);
    }

    public String customStringQueryResult(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customQueryResult(query, parametersMap);
    }

    public Boolean customBooleanQueryResult(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customBooleanQueryResult(query, parametersMap);
    }

    public Number customNumbernQueryResult(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        return advancedDatabaseObjectRepository.customNumberQueryResult(query, parametersMap);
    }
}
