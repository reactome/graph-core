package org.reactome.server.graph.service.util;

import org.apache.commons.lang3.StringUtils;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.helper.AttributeProperties;
import org.reactome.server.graph.service.helper.SchemaNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Component
public class DatabaseObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectUtils.class);

    private static Map<String,SchemaNode> map;

    private static DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    public void setDatabaseObjectRepository(DatabaseObjectRepository databaseObjectRepository){
        DatabaseObjectUtils.databaseObjectRepository = databaseObjectRepository;
    }

    @SuppressWarnings("unused")
    public static SchemaNode getGraphModelTree(Collection<SchemaClassCount> schemaClassCounts ) throws ClassNotFoundException {
        map = new HashMap<>();
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        for (SchemaClassCount schemaClassCount : schemaClassCounts) {
            Class<?> lowestClass = Object.class;
            for (String label : schemaClassCount.getLabels()) {
                if (label.equals("DBInfo")) continue;
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
    public static Map<String, Object> getAllFields(DatabaseObject databaseObject) {
        Method[] methods = databaseObject.getClass().getMethods();
        Map<String, Object> map = new TreeMap<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass") && !method.getName().equals("getId") && !method.getName().equals("getExplanation")) {
                try {
                    Object object = method.invoke(databaseObject);
                    if (object == null || object.equals("")) continue;
                    map.put(method.getName().substring(3), object);
                } catch (IllegalAccessException|InvocationTargetException e) {
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
        Set<Method> declaredMethods = new HashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get")
                    && !methodName.startsWith("getSuper")
                    && !methodName.equals("getClass")
                    && !methodName.equals("getId")) {

                AttributeProperties properties = getAttributeProperties(method);
                if (declaredMethods.contains(method)) {
                    properties.setDeclaredMethod(true);
                } else {
                    properties.setDeclaredMethod(false);
                }
                propertiesList.add(properties);
            }
        }
        return propertiesList;
    }

    public static String getIdentifier(Object id){
        if (id instanceof String) {
            String aux = trimId((String) id);
            if(aux.startsWith("REACT_")){ //In case the provided identifier is an OLD style one, we translate to the new one
                DatabaseObject dbObject = databaseObjectRepository.findByOldStId(aux);
                if(dbObject!=null) aux = dbObject.getStId();
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
        Pattern p = Pattern.compile("(\\d{4})-(\\d{4})-(\\d{4})-(\\d{4})");
        return p.matcher(id).matches();
    }

    public static boolean isStId(String id) {
        return id != null && id.startsWith("R");
    }

    public static boolean isDbId(String id) {
        return id != null && StringUtils.isNumeric(id);
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
        if(StringUtils.isAllUpperCase(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static String trimId(String id) {
        return id.trim().split("\\.")[0];
    }

    private static void recursion(Class clazz, SchemaNode oldNode, int count) {
        if (!clazz.equals(Object.class)) {
            SchemaNode node = new SchemaNode(clazz,count);
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
                map.put(clazz.getSimpleName(),node);
                recursion(clazz.getSuperclass(),node,0);
            }
        }
    }

    private static void correctCounts(SchemaNode node) {
        if (node.getChildren()!=null) {
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
        if (returnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            properties.setCardinality("+");
            if (typeArguments.length>0) {
                Class clazz = (Class) typeArguments[0];
                properties.setValueType(clazz.getSimpleName());
            }
        } else {
            Class clazz = (Class) returnType;
            properties.setCardinality("1");
            properties.setValueType(clazz.getSimpleName());
        }
        return properties;
    }


}
