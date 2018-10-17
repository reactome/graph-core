package org.reactome.server.graph.repository;

import jodd.typeconverter.TypeConverterManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.util.RepositoryUtils;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unchecked")
@Repository
public class AdvancedDatabaseObjectRepository {

    private final Neo4jOperations neo4jTemplate;

    @Autowired
    public AdvancedDatabaseObjectRepository(Neo4jOperations neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

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
                       "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|physicalEntity|crossReference|referenceGene]-(o) " +
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
                       "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|physicalEntity|crossReference|referenceGene]-(o) " +
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

    public <T> T customQueryResult(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        if (parameters == null) parameters = Collections.EMPTY_MAP;

        if (DatabaseObject.class.isAssignableFrom(clazz)) {
            return neo4jTemplate.queryForObject(clazz, query, parameters);
        }

        if (ClassUtils.isPrimitiveOrWrapper(clazz) || String.class.isAssignableFrom(clazz)) {
            final Result result = neo4jTemplate.query(query, parameters);
            if (result.iterator().hasNext()) {
                Map<String, Object> stringObjectMap = result.iterator().next();
                return TypeConverterManager.convertType(stringObjectMap.values().iterator().next(), clazz);
            } else {
                return null;
            }
        }

        final Collection<T> collection = customQueryResults(clazz, query, parameters);
        return collection.iterator().hasNext() ? collection.iterator().next() : null;
    }

    public <T> Collection<T> customQueryResults(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        if (parameters == null) parameters = Collections.EMPTY_MAP;

        if (DatabaseObject.class.isAssignableFrom(clazz)) {
            return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, parameters);
        }

        if (ClassUtils.isPrimitiveOrWrapper(clazz) || String.class.isAssignableFrom(clazz)) {
            return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, parameters);
        }

        try {
            final Collection<T> collection = new LinkedList<>();
            for (Map<String, Object> map : neo4jTemplate.query(query, parameters)) {
                collection.add(cast(clazz, null, map));
            }
            return collection;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CustomQueryException(e);
        }
    }

    /**
     * @param target     target class for source
     * @param innerClass if target class is Collection (or any subclass), the class for the elements of the collection
     * @param source     source object, compatible with target
     * @return an object of class T extracted from source
     * @throws IllegalAccessException if any of the setter/fields of target/innerType is not accesible
     */
    private <T> T cast(Class<T> target, Class innerClass, Object source) throws IllegalAccessException, InvocationTargetException, CustomQueryException {
        // Source	Target		Action
        // object	object		target.class.cast(object)
        // map 		object      mapToInstance(map, object)
        // map 		map 		target.class.cast(object)
        // list		list 		[for object in source : instance(target.innerClass, object)]
        // array 	list 		[for object in source : instance(target.innerClass, object)]
        try {
            if (source instanceof Map) {
                return mapToInstance(target, (Map) source);
            } else if (source instanceof Object[]) {
                final List list = new LinkedList();
                for (Object object : (Object[]) source)
                    list.add(cast(innerClass, null, object));
                return target.cast(list);
            } else if (source instanceof Collection) {
                final List list = new LinkedList();
                for (Object object : (Collection) source)
                    list.add(cast(innerClass, null, object));
                return target.cast(list);
            } else {
                return target.cast(source);
            }
        } catch (NullPointerException e) {
            return null;
        } catch (ClassCastException e) {
            return TypeConverterManager.convertType(source, target);
        }
    }

    private <T> T mapToInstance(Class<T> target, Map map) throws IllegalAccessException, InvocationTargetException, CustomQueryException {
        try {

            final T instance;
            final Map<String, Method> setters;
            final Map<String, Field> fields;

            if (DatabaseObject.class.isAssignableFrom(target)) {
                try {
                    map = (Map) map.get("data");
                    String sc = (String) map.remove("schemaClass");
                    Class<?> clazz = Class.forName(DatabaseObject.class.getPackage().getName() + "." + sc);
                    instance = (T) clazz.newInstance();
                    setters = getSetters(clazz);
                    fields = getAllFields(clazz);
                } catch (ClassNotFoundException e) {
                    throw new CustomQueryException(e);
                }
            } else {
                instance = target.newInstance();
                setters = getSetters(target);
                fields = getAllFields(target);
            }

            for (Object key : map.keySet()) {
                String field = String.valueOf(key);
                Object value = map.get(field);
                if (value == null) continue;
                //1st -> Check whether the setter method is present
                Method setFieldMethod = setters.get("set" + StringUtils.capitalize(field));
                if (setFieldMethod != null) {
                    // If the setter exists, it is invoked
                    setFieldMethod.setAccessible(true);
                    final Class subTarget = setFieldMethod.getParameterTypes()[0];
                    final Class innerClass = getInnerClass(setFieldMethod);
                    final Object object = cast(subTarget, innerClass, value);
                    setFieldMethod.invoke(instance, object);
                } else {
                    // The setter does not exist
                    //2nd -> Check whether there is a field with the same name
                    Field classField = fields.get(field);
                    if (classField == null) throw new CustomQueryException(String.format("NoSuchFieldException: '%s'.'%s'", target.getSimpleName(), field));
                    classField.setAccessible(true);
                    final Class subTarget = classField.getType();
                    final Class innerClass = getInnerClass(classField);
                    final Object object = cast(subTarget, innerClass, value);
                    classField.set(instance, object); //The field exists and the value is set

                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw new CustomQueryException(e);
        }
    }


    private Class getInnerClass(Field field) {
        final Type paramType = field.getGenericType();
        return paramType instanceof ParameterizedType
                ? (Class) ((ParameterizedType) paramType).getActualTypeArguments()[0]
                : null;
    }

    private Class getInnerClass(Method method) {
        final Type paramType = method.getGenericParameterTypes()[0];
        return paramType instanceof ParameterizedType
                ? (Class) ((ParameterizedType) paramType).getActualTypeArguments()[0]
                : null;
    }

    private Map<String, Field> getAllFields(Class<?> type) {
        Map<String, Field> map = new HashMap<>();
        for (Field field : type.getDeclaredFields()) {
            map.put(field.getName(), field);
        }
        if (type.getSuperclass() != null) {
            map.putAll(getAllFields(type.getSuperclass()));
        }
        return map;
    }

    private Map<String, Method> getSetters(Class<?> type) {
        Map<String, Method> map = new HashMap<>();
        for (Method method : type.getMethods()) {
            if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                map.put(method.getName(), method);
            }
        }
        if (type.getSuperclass() != null) {
            map.putAll(getSetters(type.getSuperclass()));
        }
        return map;
    }

    /**
     * Neo4j results always return the Object (wrapper) as an Array (if it is collection). However if we are mapping
     * an object which attribute is a int[] e.g then it does not 'boxing', then this method checks the type and
     * return the proper Array of primitive
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
     * The types are: Array of primitive, Collection of [String, Number, primitives and CustomObjects] and Custom Objects.
     * A external type convert is used to convert the Result "stringObjectMap" once Neo4j returns
     * an array of Integer for a list of number and we must be able to convert it to whatever type the final user
     * has defined in his Custom Object. Obviously the type conversion is going to check the type compatibility.
     * For that we use the jodd.TypeConverter and we are using Java Reflection for the Custom Objects - we manually instantiate
     * them and recursively call the result list.
     */
    private <T> void setFields(T instance, Field field, Map<String, Object> stringObjectMap) throws Exception {
        String fieldName = field.getName();
        Object object = stringObjectMap.get(fieldName);
        if (object != null) {
            field.setAccessible(true);

            // An array of primitives do not box automatically then we cast it manually using ArrayUtils and the attribute type
            if (field.getType().isArray() && field.getType().getComponentType().isPrimitive()) {
                field.set(instance, toPrimitiveArray(object, field.getType()));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                //The returned results are normally stored in an Array, then we need to convert in case the attribute is a Collection
                ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];

                // Parametrised type is String and the convertToCollection is able to convert that
                if (stringListClass.isAssignableFrom(String.class) || Number.class.isAssignableFrom(stringListClass) || stringListClass.isPrimitive()) {
                    //noinspection unchecked
                    field.set(instance, TypeConverterManager.convertToCollection(object, (Class<? extends Collection>) field.getType(), stringListClass));
                } else {
                    // Parametrised type is a Custom Object so we have to create the list, create the objects
                    // add them into the list and set attribute in the main class.
                    Collection<T> customCollection;
                    if (field.getType().isAssignableFrom(List.class)) {
                        customCollection = new ArrayList<>();
                    } else if (field.getType().isAssignableFrom(Set.class)) {
                        customCollection = new HashSet<>();
                    } else {
                        throw new Exception("Couldn't get the class name of the given collection [" + field.getType() + "]");
                    }

                    if (object instanceof Map[]) {
                        // The object returned by Neo4j is an array of LinkedHashMap.
                        LinkedHashMap<String, Object>[] allLinkedHashMap = (LinkedHashMap<String, Object>[]) object;
                        Collection<Field> fields = getAllFields(stringListClass).values();
                        for (LinkedHashMap<String, Object> entry : allLinkedHashMap) {
                            T customInstance = createAndPopulateObject(stringListClass, fields, entry);
                            customCollection.add(customInstance);
                        }
                    }
                    // set the list in the main class
                    field.set(instance, customCollection);
                }
            } else if (field.getType().isAssignableFrom(String.class) || Number.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
                try {
                    // The attribute is String, Number or an array we know how to convert
                    field.set(instance, TypeConverterManager.convertType(object, field.getType()));
                } catch (Exception ex) {
                    field.set(instance, null);
                }
            } else {
                // The attribute is a Custom Object that needs to be instantiated.
                Collection<Field> customFields = getAllFields(field.getType()).values();
                T customInstance = createAndPopulateObject(field.getType(), customFields, (Map<String, Object>) object);
                field.set(instance, customInstance);
            }
        }
    }

    /**
     * This method is called in the setFields method which calls recursively in order to create and populate the objects themselves.
     *
     * @param clazz        the class to be instantiate
     * @param customFields attributes of given class
     * @param object       result map of the cypher query, key=String(name of attribute) value=object result of the given attribute
     * @throws Exception in case we can't create new instances
     */
    private <T> T createAndPopulateObject(Class<?> clazz, Collection<Field> customFields, Map<String, Object> object) throws Exception {
        T customInstance = (T) clazz.newInstance();
        for (Field customField : customFields) {
            setFields(customInstance, customField, object);
        }
        return customInstance;
    }
}
