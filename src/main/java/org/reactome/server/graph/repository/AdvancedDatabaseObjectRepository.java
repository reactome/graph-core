package org.reactome.server.graph.repository;

import jodd.typeconverter.TypeConverterManager;
import org.apache.commons.lang3.ArrayUtils;
import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.util.RepositoryUtils;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFields;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unchecked")
@Repository
public class AdvancedDatabaseObjectRepository {

    @Autowired
    private Neo4jOperations neo4jTemplate;

//    The following two methods were thought to provide order in the lists of attributes, but now is done with the
//    relationship objects in the model side (instead of with Cypher)
//    public <T extends DatabaseObject> T findById(Long dbId) {
//        String query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
//        Map<String, Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        neo4jTemplate.clear();
//        Result result = neo4jTemplate.query(query, map, true);
//        if (result != null && result.iterator().hasNext()) {
//            return (T) result.iterator().next().get("n");
//        }
//        return null;
//    }
//
//    public <T extends DatabaseObject> T findById(String stId) {
//        String query = "MATCH (n:DatabaseObject{stId:{stId}})-[r]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
//        Map<String, Object> map = new HashMap<>();
//        map.put("stId", stId);
//        neo4jTemplate.clear();
//        Result result = neo4jTemplate.query(query, map, true);
//        if (result != null && result.iterator().hasNext())
//            return (T) result.iterator().next().get("n");
//        return null;
//    }

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadByProperty(clazz, property, value, depth);
    }

    public <T extends DatabaseObject> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadAllByProperty(clazz, property, value, depth);
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findById(Long dbId, Integer limit) {
        String query = "MATCH (n:DatabaseObject{dbId:{dbId}}) " +
                       "OPTIONAL MATCH (n)-[r]-(m) " +
                       "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC LIMIT {limit}";
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        map.put("limit", limit);
        neo4jTemplate.clear();
        Result result = neo4jTemplate.query(query, map, true);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public <T extends DatabaseObject> T findById(String stId, Integer limit) {
        String query = "MATCH (n:DatabaseObject{stId:{stId}}) " +
                       "OPTIONAL MATCH (n)-[r]-(m) " +
                       "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC LIMIT {limit}";
        Map<String, Object> map = new HashMap<>();
        map.put("stId", stId);
        map.put("limit", limit);
        neo4jTemplate.clear();
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public <T extends DatabaseObject> T findEnhancedObjectById(Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:{dbId}}) " +
                       "OPTIONAL MATCH (n)-[r1]-(m) " +
                       "OPTIONAL MATCH (m)-[r2:species]->(s) " +
                       "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|catalyzedEvent|physicalEntity|crossReference|referenceGene]-(o) " +
                       "RETURN n,r1,m,r2,s,r3,o";
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public <T extends DatabaseObject> T findEnhancedObjectById(String stId) {
        String query = "MATCH (n:DatabaseObject{stId:{stId}}) " +
                       "OPTIONAL MATCH (n)-[r1]-(m) " +
                       "OPTIONAL MATCH (m)-[r2:species]->(s) " +
                       "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|catalyzedEvent|physicalEntity|crossReference|referenceGene]-(o) " +
                       "RETURN n,r1,m,r2,s,r3,o";
        Map<String, Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public <T extends DatabaseObject> T findById(Long dbId, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}}) " +
                        "OPTIONAL MATCH (n)-[r]->(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}}) " +
                        "OPTIONAL MATCH (n)<-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject{dbId:{dbId}}) " +
                        "OPTIONAL MATCH (n)-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public <T extends DatabaseObject> T findById(String stId, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stId:{stId}}) " +
                        "OPTIONAL MATCH (n)-[r]->(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stId:{stId}})" +
                        "OPTIONAL MATCH (n)<-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject{stId:{stId}})" +
                        "OPTIONAL MATCH (n)-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public <T extends DatabaseObject> T findById(Long dbId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public <T extends DatabaseObject> T findById(String stId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stId:{stId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stId:{stId}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{stId:{stId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("n");
        return null;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.dbId IN {dbIds} " +
                        "OPTIONAL MATCH (n)-[r]->(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.dbId IN {dbIds} " +
                        "OPTIONAL MATCH (n)<-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.dbId IN {dbIds} " +
                        "OPTIONAL MATCH (n)-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dbIds", dbIds);
        Result result = neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.stId IN {stIds} " +
                        "OPTIONAL MATCH (n)-[r]->(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.stId IN {stIds} " +
                        "OPTIONAL MATCH (n)<-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject) " +
                        "WHERE n.stId IN {stIds} " +
                        "OPTIONAL MATCH (n)-[r]-(m) " +
                        "RETURN n,r,m ORDER BY TYPE(r) ASC, r.order ASC";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("stIds", stIds);
        Result result = neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dbIds", dbIds);
        Result result = neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("stIds", stIds);
        Result result = neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findCollectionByRelationship(Long dbId, String clazz, Class<?> collectionClass, RelationshipDirection direction, String... relationships) {
        Result result = queryRelationshipTypesByDbId(dbId, clazz, direction, relationships);

        Collection<DatabaseObject> databaseObjects;
        if (collectionClass.getName().equals(Set.class.getName())) {
            databaseObjects = new HashSet<>();
            //No need to check stoichiometry
            for (Map<String, Object> stringObjectMap : result) {
                databaseObjects.add((DatabaseObject) stringObjectMap.get("m"));
            }
        } else {
            databaseObjects = new ArrayList<>();
            //Here stoichiometry has to be taken into account
            for (Map<String, Object> stringObjectMap : result) {
                for (int i = 0; i < (int) stringObjectMap.get("n"); ++i) {
                    databaseObjects.add((DatabaseObject) stringObjectMap.get("m"));
                }
            }
        }
        return databaseObjects.isEmpty() ? null : databaseObjects;
    }

    public <T extends DatabaseObject> T findByRelationship(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        Result result = queryRelationshipTypesByDbId(dbId, clazz, direction, relationships);

        if (result != null && result.iterator().hasNext())
            return (T) result.iterator().next().get("m");
        return null;
    }

    /**
     * During the Lazy-Loading strategy, we need to query pointing to a relationship type and either gets back a single
     * DatabaseObject or a Collection of DatabaseObject.
     * This method queries the Graph and returns it as a Result object that will be parsed in the findByRelationship
     * and findCollectionByRelationship accordingly.
     */
    private Result queryRelationshipTypesByDbId(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m:" + clazz + ") RETURN m, r.stoichiometry AS n ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            case INCOMING:
                query = "MATCH (:DatabaseObject{dbId:{dbId}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m:" + clazz + ") RETURN m, r.stoichiometry AS n ORDER BY TYPE(r) ASC, r.order ASC";
                break;
            default: //UNDIRECTED
                query = "MATCH (:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m:" + clazz + ") RETURN m, r.stoichiometry AS n ORDER BY TYPE(r) ASC, r.order ASC";
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);

        return neo4jTemplate.query(query, map);
    }

    // ----------------------------------------- Custom Query Methods --------------------------------------------------

    public <T> Collection<T> customQueryForObjects(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        Collection<T> instancesResult = new ArrayList<>();
        Result result = neo4jTemplate.query(query, parametersMap);
        Field[] fields = getAllFields(clazz);

        try {
            for (Map<String, Object> stringObjectMap : result) {
                T instance = clazz.newInstance();
                for (Field field : fields) {
                    setFields(instance, field, stringObjectMap);
                }
                instancesResult.add(instance);
            }
        } catch (Throwable e) {
            throw new CustomQueryException(e);
        }

        return instancesResult;
    }

    public <T> T customQueryForObject(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        Result result = neo4jTemplate.query(query, parametersMap);
        Field[] fields = getAllFields(clazz);

        try {
            if (result.iterator().hasNext()) {
                Map<String, Object> stringObjectMap = result.iterator().next();
                T instance = clazz.newInstance();
                for (Field field : fields) {
                    setFields(instance, field, stringObjectMap);
                }
                return instance;
            }
        } catch (Throwable e) {
            throw new CustomQueryException(e);
        }

        return null;
    }

    public String customQueryResult(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        Result result = neo4jTemplate.query(query, parametersMap);

        try {
            if (result.iterator().hasNext()) {
                Map<String, Object> stringObjectMap = result.iterator().next();
                return TypeConverterManager.convertType(stringObjectMap.values().iterator().next(), String.class);
            }
        } catch (Throwable e) {
            throw new CustomQueryException(e);
        }

        return null;
    }

    public Boolean customBooleanQueryResult(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        Result result = neo4jTemplate.query(query, parametersMap);

        try {
            if (result.iterator().hasNext()) {
                Map<String, Object> stringObjectMap = result.iterator().next();
                return TypeConverterManager.convertType(stringObjectMap.values().iterator().next(), Boolean.class);
            }
        } catch (Throwable e) {
            throw new CustomQueryException(e);
        }

        return null;
    }

    public Collection<String> customQueryResults(String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        // result is an ArrayList which each position has a Map<String, Object> and the map.key is the label we have
        // present in the query MATCH ... RETURN n.dbId as identifier, then "identifier" and the Object is List
        Result result = neo4jTemplate.query(query, parametersMap);

        List<Object> list = new ArrayList<>();
        try {
            for (Map<String, Object> stringObjectMap : result) {
                // for a list of String result the important bit are the values - collect the and add into List<Object>
                list.addAll(stringObjectMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
            }

            // convert list of Object into a list of strings.
            return TypeConverterManager.convertToCollection(list, List.class, String.class);

        } catch (Throwable e) {
            throw new CustomQueryException(e);
        }
    }



    public <T> Collection<T> customQueryForDatabaseObjects(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (!DatabaseObject.class.isAssignableFrom(clazz))
            throw new CustomQueryException(clazz.getSimpleName() + " does not belong to our data model");

        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        try {
            return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, parametersMap);
        } catch (RuntimeException e) {
            throw new CustomQueryException(e);
        }
    }

    public <T extends DatabaseObject> T customQueryForDatabaseObject(Class<T> clazz, String query, Map<String, Object> parametersMap) throws CustomQueryException {
        if (!DatabaseObject.class.isAssignableFrom(clazz))
            throw new CustomQueryException(clazz.getSimpleName() + " does not belong to our data model");

        if (parametersMap == null) //noinspection unchecked
            parametersMap = Collections.EMPTY_MAP;

        try {
            return neo4jTemplate.queryForObject(clazz, query, parametersMap);
        } catch (RuntimeException e) {
            throw new CustomQueryException(e);
        }
    }

    /**
     * Neo4j results always return the Object (wrapper)
     * as an Array (if it is collection). However if we are
     * mapping an object which attribute is a int[] e.g then it
     * does not 'boxing', then this method checks the type
     * and return the proper Array of primitive .
     */
    private Object toPrimitiveArray(Object value, Class type) {
        if (type == byte[].class) {
            return ArrayUtils.toPrimitive((Byte[]) value);
        } else if (type == short[].class) {
            return ArrayUtils.toPrimitive((Short[]) value);
        } else if (type == int[].class) {
            return ArrayUtils.toPrimitive((Integer[]) value);
        } else if (type == float[].class) {
            return ArrayUtils.toPrimitive((Float[]) value);
        } else if (type == double[].class) {
            return ArrayUtils.toPrimitive((Double[]) value);
        } else if (type == char[].class) {
            return ArrayUtils.toPrimitive((Character[]) value);
        } else if (type == long[].class) {
            return ArrayUtils.toPrimitive((Long[]) value);
        } else if (type == boolean[].class) {
            return ArrayUtils.toPrimitive((Boolean[]) value);
        }
        // version 3.5 we can perform a single call like - return ArrayUtils.toPrimitive(value);
        return null;
    }

    /**
     * Method that uses Java Reflection in order to set the attributes of a specific instance.
     * The attribute is handled in different ways depends on its type.
     * The types are: Array of primitive, Collection and others.
     * A external type convert is used to convert the Result "stringObjectMap" once Neo4j returns
     * an array of Integer for a list of number and we must be able to convert it to whatever type the final user
     * has defined in his Custom Object. Obviously the type conversion is going to check the type compatibility.
     */
    private <T> void setFields(T instance, Field field, Map<String, Object> stringObjectMap) throws IllegalAccessException {
        String fieldName = field.getName();
        Object object = stringObjectMap.get(fieldName);
        if (object != null) {
            field.setAccessible(true);
            if (field.getType().isArray() && field.getType().getComponentType().isPrimitive()) {
                // An array of primitives do not box automatically then we cast it manually using ArrayUtils and the attribute type
                field.set(instance, toPrimitiveArray(object, field.getType()));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                //The returned results are normally stored in an Array, then we need to convert in case the attribute is a Collection
                ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
                //noinspection unchecked
                field.set(instance, TypeConverterManager.convertToCollection(object, (Class<? extends Collection>) field.getType(), stringListClass));
            } else {
                // Other fields, no problem with type conversion
                field.set(instance, TypeConverterManager.convertType(object, field.getType()));
            }
        }
    }
}
