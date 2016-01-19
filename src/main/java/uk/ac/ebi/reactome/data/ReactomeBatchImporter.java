package uk.ac.ebi.reactome.data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.relationship.Input;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class ReactomeBatchImporter {

    private static final Logger logger = Logger.getLogger(ReactomeBatchImporter.class);

    /**
     * Neo4j Database location. WARNING: This folder will be deleted at the start of data import
     */
    private static final String dataDir = "/var/lib/neo4j/data/graph.db";

    private static BatchInserter batchInserter;

    private static final Map<Class, List<Method>> primitiveMethodMap = new HashMap<>();
    private static final Map<Class, List<Method>> relationMethodMap = new HashMap<>();
    private static final Map<Class, List<Method>> setterMethodMap = new HashMap<>();
    private static final Map<Class, Label[]> labelMap = new HashMap<>();
    private static final Map<Long,Long> dbIds = new HashMap<>();

//    @PostConstruct
    public void importAll() {

        prepareDatabase();
        List<DatabaseObject> frontPageItems = DatabaseObjectFactory.loadFrontPageItems();
        logger.info("Started importing " + frontPageItems.size() + " top level pathways");
        for (DatabaseObject frontPageItem : frontPageItems) {
            importDatabaseObject(frontPageItem);
            logger.info("finished"  + frontPageItem.getDisplayName());
        }
        logger.info("All top level pathways have been imported to Neo4j");
        batchInserter.shutdown();
    }

    public void importById(String id) {

        prepareDatabase();
        DatabaseObject databaseObject = DatabaseObjectFactory.createObject(id);
        importDatabaseObject(databaseObject);
        batchInserter.shutdown();
    }

    private void prepareDatabase() {

        cleanDatabase();
        batchInserter = BatchInserters.inserter(dataDir);
        createConstraints();
    }

    private void createConstraints() {

        createSchemaConstraint(DynamicLabel.label("DatabaseObject"), "dbId");
    }

    private Long importDatabaseObject(DatabaseObject databaseObject) {

        Class clazz = databaseObject.getClass();
        setUpMethods(clazz);
        Long id = saveDatabaseObject(databaseObject, clazz);
        if (!dbIds.containsKey(databaseObject.getDbId())) { //to saveDatabase object
            dbIds.put(databaseObject.getDbId(), id);
        }
        List<Method> methods = relationMethodMap.get(clazz);
        if (methods==null) return id;
        for (Method method : methods) {
            String methodName = method.getName();
            String propertyName = methodName.substring(3);
            String relationName = DatabaseObjectFactory.lowerFirst(propertyName);
            try {
                Object object = method.invoke(databaseObject);
                if (object == null) continue;
                if (object instanceof Collection) {
                    for (Object obj : (Collection)object) {
                        loadDatabaseObject(id, obj, relationName);
                    }
                } else {
                    loadDatabaseObject(id, object, relationName);
                }
            } catch (IllegalAccessException|InvocationTargetException e) {
                logger.error("Error occurred then trying to invoke method " + relationName + " of Object " +
                        databaseObject.getSchemaClass());
            }
            Method[] methodArray = databaseObject.getClass().getMethods();
            for (Method methods1 : methodArray) {
                if(methods1.getName().startsWith("set") && propertyName.equals(methods1.getName().substring(3))) {
                    try {
                        methods1.invoke(databaseObject, new Object[]{null});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return id;
    }

    private Long saveDatabaseObject(DatabaseObject databaseObject, Class clazz) {

        Label[] labels = getLabels(clazz);
        List<Method> methods = primitiveMethodMap.get(clazz);
        Map<String,Object> properties = new HashMap<>();
        for (Method method : methods) {
            String methodName = method.getName();
            String fieldName = DatabaseObjectFactory.lowerFirst(methodName.substring(3));
            try {
                Object object = method.invoke(databaseObject);
                if (object == null) continue;
                if (object instanceof Collection) {
                    properties.put(fieldName,((List) object).toArray(new String[((List) object).size()]));
                } else {
                    properties.put(fieldName, object);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("Error occurred then trying to invoke method " + " of Object " +
                        databaseObject.getSchemaClass());
            }
        }
        return batchInserter.createNode(properties, labels);
    }

    private void loadDatabaseObject (Long oldId, Object object, String methodName) {

        if (object instanceof DatabaseObject) {
            DatabaseObject databaseObject = (DatabaseObject) object;
            Long dbId = databaseObject.getDbId();
            Long newId;
            if (!dbIds.containsKey(dbId)) {
                databaseObject.load();
                newId = importDatabaseObject(databaseObject);
                databaseObject = null; // no effect
            } else {
                newId = dbIds.get(dbId);
            }
            RelationshipType relationshipType = DynamicRelationshipType.withName(methodName);
            batchInserter.createRelationship(oldId, newId, relationshipType, null);
        }
        else if(object instanceof Input) {
            Input input = (Input) object;
            Long dbId = input.getPhysicalEntity().getDbId();
            Long newId;
            if (!dbIds.containsKey(dbId)) {
                input.getPhysicalEntity().load();
                newId = importDatabaseObject(input.getPhysicalEntity());
            } else {
                newId = dbIds.get(dbId);
            }
            Map<String,Object> properties = new HashMap<>();
            properties.put("cardinality",input.getCardinality());
            RelationshipType relationshipType = DynamicRelationshipType.withName(methodName);
            batchInserter.createRelationship(oldId, newId, relationshipType, properties);
        }
    }

    private void setUpMethods(Class clazz) {

        if(!relationMethodMap.containsKey(clazz) && !primitiveMethodMap.containsKey(clazz)) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass") && !methodName.startsWith("getSuper") && !methodName.equals("getSchemaClass")) {
                    Type returnType = method.getGenericReturnType();
                    if (returnType instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType) returnType;
                        Type[] typeArguments = type.getActualTypeArguments();
                        for (Type typeArgument : typeArguments) {
                            Class typeArgClass = (Class) typeArgument;
                            if (DatabaseObject.class.isAssignableFrom(typeArgClass) ) {
                                setMethods(relationMethodMap, clazz, method);

                            } else if (Input.class.isAssignableFrom(typeArgClass)) {
                                setMethods(relationMethodMap, clazz, method);
                            }
                            else {
                                setMethods(primitiveMethodMap, clazz, method);
                            }
                        }
                    } else {
                        if (DatabaseObject.class.isAssignableFrom(method.getReturnType())) {
                            setMethods(relationMethodMap, clazz, method);
                        } else {
                            setMethods(primitiveMethodMap, clazz, method);
                        }
                    }
                }
                if (methodName.startsWith("set") && !methodName.equals("setDisplayName")) {
                    setMethods(setterMethodMap, clazz,method);
                }
            }
        }
    }

    private void setMethods (Map<Class,List<Method>> map, Class clazz, Method method) {
        if(map.containsKey(clazz)) {
            (map.get(clazz)).add(method);
        } else {
            List<Method> methodList = new ArrayList<>();
            methodList.add(method);
            map.put(clazz, methodList);
        }
    }

    private Label[] getLabels(Class clazz) {

        if(!labelMap.containsKey(clazz)) {
            Label[] labels = getAllClassNames(clazz);
            labelMap.put(clazz, labels);
            return labels;
        } else {
            return labelMap.get(clazz);
        }
    }

    private Label[] getAllClassNames(Class clazz) {

        List<?> superClasses = ClassUtils.getAllSuperclasses(clazz);
        List<Label> labels = new ArrayList<>();
        labels.add(DynamicLabel.label(clazz.getSimpleName()));
        for (Object object : superClasses) {
            Class superClass = (Class) object;
            if(!superClass.getSimpleName().equals("Object")) {
                labels.add(DynamicLabel.label(superClass.getSimpleName()));
            }
        }
        return labels.toArray(new Label[labels.size()]);
    }

    private static void createSchemaConstraint (Label label, String name) {

        try {
            batchInserter.createDeferredConstraint(label).assertPropertyIsUnique(name).create();
        } catch (ConstraintViolationException e) {
            logger.warn("Could not create Constraint on " + label + " " + name);
        }
    }

    private  void cleanDatabase () {

        try {
            FileUtils.cleanDirectory(new File(dataDir));
        } catch (IOException |IllegalArgumentException e) {
            logger.warn("An error occurred while cleaning the old database");
        }
    }

}