package org.reactome.server.graph.service.util;

import org.apache.commons.lang3.StringUtils;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.helper.AttributeProperties;
import org.reactome.server.graph.service.helper.SchemaNode;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
@Component
public class DatabaseObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectUtils.class);

    private static Map<String, SchemaNode> map;

    private static DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    public void setDatabaseObjectRepository(DatabaseObjectRepository databaseObjectRepository) {
        DatabaseObjectUtils.databaseObjectRepository = databaseObjectRepository;
    }

    @SuppressWarnings("unused")
    public static SchemaNode getGraphModelTree(Collection<SchemaClassCount> schemaClassCounts) throws ClassNotFoundException {
        map = new HashMap<>();
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        for (SchemaClassCount schemaClassCount : schemaClassCounts) {
            Class<?> lowestClass = Object.class;
            for (String label : schemaClassCount.getLabels()) {
                Class clazz = Class.forName(packageName + label);
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            }
            recursion(lowestClass, null, schemaClassCount.getCount());
        }
        SchemaNode n = map.get(DatabaseObject.class.getSimpleName());
        correctCounts(n);
        return n;
    }

    @SuppressWarnings("unused")
    public static Map<String, Object> getAllFields(DatabaseObject databaseObject, boolean showUndefinedAttributes) {
        Method[] methods = databaseObject.getClass().getMethods();
        Map<String, Object> map = new TreeMap<>();
        for (Method method : methods) {
            // Filtering fields that we don't want to show
            // in the Schema Details.
            if (method.getAnnotation(ReactomeSchemaIgnore.class) == null
                    && method.getName().startsWith("get")
                    && !method.getName().equals("getClass")) {

                try {
                    Object object = method.invoke(databaseObject);
                    if (!showUndefinedAttributes && isEmpty(object)) continue;

                    // For this four methods we want to invoke fetch{NAME} rather than the getter.
                    switch (method.getName()) {
                        case "getInput":
                            map.put(lowerFirst(method.getName().substring(3)), databaseObject.getClass().getMethod("fetchInput").invoke(databaseObject));
                            break;
                        case "getOutput":
                            map.put(lowerFirst(method.getName().substring(3)), databaseObject.getClass().getMethod("fetchOutput").invoke(databaseObject));
                            break;
                        case "getHasComponent":
                            map.put(lowerFirst(method.getName().substring(3)), databaseObject.getClass().getMethod("fetchHasComponent").invoke(databaseObject));
                            break;
                        case "getRepeatedUnit":
                            map.put(lowerFirst(method.getName().substring(3)), databaseObject.getClass().getMethod("fetchRepeatedUnit").invoke(databaseObject));
                            break;
                        default:
                            map.put(lowerFirst(method.getName().substring(3)), object);
                            break;
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.error("An error occurred while invoking " + databaseObject.getDisplayName() + " with method " + method.getName());
                }
            }
        }
        return map;
    }

    @SuppressWarnings("unused")
    public static Set<AttributeProperties> getAttributeTable(String className) throws ClassNotFoundException {
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        Class clazz = Class.forName(packageName + className);
        Set<AttributeProperties> propertiesList = new TreeSet<>();

        while (clazz != null && !clazz.getClass().equals(Object.class)) {
            for (Method method : clazz.getDeclaredMethods()) {
                String methodName = method.getName();

                if (method.getAnnotation(ReactomeSchemaIgnore.class) == null
                        && methodName.startsWith("get")
                        && !methodName.equals("getClass")
                        && !methodName.startsWith("getSuperclass")
                        && !methodName.contains("_aroundBody")) { // aspectj injected methods

                    AttributeProperties properties = getAttributeProperties(method);
                    properties.setOrigin(clazz);

                    propertiesList.add(properties);
                }
            }

            // Didn't find the field in the given class. Check the Superclass.
            clazz = clazz.getSuperclass();
        }
        return propertiesList;
    }

    @SuppressWarnings("unused")
    public static Set<AttributeProperties> getReferrals(String className) throws ClassNotFoundException {
        String packageName = DatabaseObject.class.getPackage().getName();

        Class target = Class.forName(packageName + "." + className);
        Set<Class> targets = extractSuperClasses(target, true);

        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends DatabaseObject>> classes = reflections.getSubTypesOf(DatabaseObject.class);

        Set<AttributeProperties> propertiesList = new TreeSet<>();
        for (Class<? extends DatabaseObject> clazz : classes) {
            if (targets.contains(clazz)) continue;
            for (Method method : clazz.getDeclaredMethods()) {
                String methodName = method.getName();
                if (method.getAnnotation(ReactomeSchemaIgnore.class) == null
                        && methodName.startsWith("get")
                        && !methodName.equals("getClass")
                        && !methodName.startsWith("getSuperclass")
//                        && !methodName.equals("getClassName")
//                        && !methodName.equals("getId")
                        && !methodName.contains("_aroundBody")) { // aspectj injected methods

                    AttributeProperties properties = getReferralProperties(method, targets);
                    if (properties != null) {
                        properties.setOrigin(clazz);
                        propertiesList.add(properties);
                    }
                }
            }
        }
        return propertiesList;
    }

    private static Set<Class> extractSuperClasses(Class target, boolean includeTargetClass) {
        Set<Class> targets = new HashSet<>();
        if (!includeTargetClass) target = target.getSuperclass();
        while (target != null && !target.getClass().equals(Object.class)) {
            targets.add(target);
            target = target.getSuperclass();
        }
        return targets;
    }

    public static String getIdentifier(Object id) {
        if (id instanceof DatabaseObject) {
            return "" + ((DatabaseObject) id).getDbId();
        } else if (id instanceof String) {
            String aux = trimId((String) id);
            if (aux.startsWith("REACT_")) { //In case the provided identifier is an OLD style one, we translate to the new one
                String stId = databaseObjectRepository.findNewStId(aux);
                if (stId != null) aux = stId;
            }
            return aux;
        } else if (id instanceof Number && !(id instanceof Double)) {
            return id.toString();
        }
        return null;
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        return p.matcher(email).matches();
    }


    public static boolean isOrcidId(String id) {
        Pattern p = Pattern.compile("(\\d{4})-(\\d{4})-(\\d{4})-(\\d{3}[0-9X])");
        return p.matcher(id).matches();
    }

    public static boolean isStId(String id) {
        return id != null && id.startsWith("R");
    }

    public static boolean isDbId(String id) {
        return StringUtils.isNumeric(id);
    }

    public static Class getClassForName(String className) throws ClassNotFoundException {
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        return Class.forName(packageName + className);
    }

    public static String getSchemaClass(Collection<String> labels) {
        Class<?> lowestClass = Object.class;
        for (String label : labels) {
            try {
                Class clazz = getClassForName(label);
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            } catch (ClassNotFoundException e) {
                logger.error("Class given has not been found in the model", e);
            }
        }
        return lowestClass.getSimpleName();
    }

    public static String lowerFirst(String str) {
        if (StringUtils.isAllUpperCase(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * @param object an object
     * @return true the object is null, an empty list or an empty string
     */
    private static boolean isEmpty(Object object){
        if (object == null) return true;

        if (object instanceof Collection){
            Collection c = (Collection) object;
            return c.isEmpty();
        }

        return object.equals("");
    }

    private static String trimId(String id) {
        return id.trim().split("\\.")[0];
    }

    private static void recursion(Class clazz, SchemaNode oldNode, int count) {
        if (!clazz.equals(Object.class)) {
            SchemaNode node = new SchemaNode(clazz, count);
            if (map.containsKey(clazz.getSimpleName())) {
                if (oldNode != null) {
                    map.get(clazz.getSimpleName()).addChild(oldNode);
                } else {
                    SchemaNode existingNode = map.get(clazz.getSimpleName());
                    existingNode.setCount(existingNode.getCount() + count);
                }
            } else {
                if (oldNode != null) {
                    node.addChild(oldNode);
                }
                map.put(clazz.getSimpleName(), node);
                recursion(clazz.getSuperclass(), node, 0);
            }
        }
    }

    private static void correctCounts(SchemaNode node) {
        if (node.getChildren() != null) {
            for (SchemaNode node1 : node.getChildren()) {
                correctCounts(node1);
                node.setCount(node.getCount() + node1.getCount());
            }
        }
    }

    private static AttributeProperties getAttributeProperties(Method method) {
        AttributeProperties properties = new AttributeProperties();
        properties.setName(lowerFirst(method.getName().substring(3)));
        Type returnType = method.getGenericReturnType();
        Annotation annotation = method.getAnnotation(ReactomeAllowedClasses.class);
        if (returnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            properties.setCardinality("+");
            if (typeArguments.length > 0) {
                if (annotation == null) {
                    properties.addAttributeClass((Class) typeArguments[0]);
                } else {
                    for (Class<? extends DatabaseObject> clazz : ((ReactomeAllowedClasses) annotation).allowed()) {
                        properties.addAttributeClass(clazz);
                    }
                }
            }
        } else {
            properties.setCardinality("1");
            if (annotation == null) {
                properties.addAttributeClass((Class) returnType);
            } else {
                for (Class<? extends DatabaseObject> clazz : ((ReactomeAllowedClasses) annotation).allowed()) {
                    properties.addAttributeClass(clazz);
                }
            }
        }
        return properties;
    }

    private static AttributeProperties getReferralProperties(Method method, Collection<Class> targets) {
        AttributeProperties properties = new AttributeProperties();
        properties.setName(lowerFirst(method.getName().substring(3)));
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            properties.setCardinality("+");
            if (typeArguments.length > 0) {
                Class clazz = (Class) typeArguments[0];
                if (!targets.contains(clazz)) return null;
                properties.addAttributeClass(clazz);
            }
        } else {
            properties.setCardinality("1");
            Annotation annotation = method.getAnnotation(ReactomeAllowedClasses.class);
            if (annotation == null) {
                Class clazz = (Class) returnType;
                if (!targets.contains(clazz)) return null;
                properties.addAttributeClass(clazz);
            } else {
                ReactomeAllowedClasses allocatedClasses = (ReactomeAllowedClasses) annotation;
                boolean found = false;
                for (Class<? extends DatabaseObject> clazz : allocatedClasses.allowed()) {
                    if (targets.contains(clazz)) {
                        properties.addAttributeClass(clazz);
                        found = true;
                    }
                }
                if(!found) return null;
            }
        }
        return properties;
    }
}
