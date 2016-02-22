package uk.ac.ebi.reactome.service.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.service.helper.AttributeProperties;
import uk.ac.ebi.reactome.service.helper.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 22.02.16.
 */
public class DatabaseObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectUtils.class);

    private static Map<String,Node> map;

    @SuppressWarnings("unused")
    public static Node getGraphModelTree(Collection<LabelsCount> labelsCounts ) throws ClassNotFoundException {
        map = new HashMap<>();
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        for (LabelsCount labelsCount : labelsCounts) {
            Class lowestClass = Object.class;
            for (String label : labelsCount.getLabels()) {
                Class clazz = Class.forName(packageName + label);
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            }
            recursion(lowestClass, null, labelsCount.getCount());
        }
        Node n = map.get(DatabaseObject.class.getSimpleName());
        correctCounts(n);
        return n;
    }

    @SuppressWarnings("unused")
    public static Map<String, Object> getAllFields(DatabaseObject databaseObject) {
        Method[] methods = databaseObject.getClass().getMethods();
        Map<String, Object> map = new TreeMap<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass") && !method.getName().equals("getId")) {
                try {
                    Object object = method.invoke(databaseObject);
                    if (object == null) continue;
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

                AttributeProperties properties = (getAttributeProperties(method));
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


    public static Class getClassForName(String className) throws ClassNotFoundException {
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        return Class.forName(packageName + className);
    }

    public static String lowerFirst(String str) {
        if(StringUtils.isAllUpperCase(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static void recursion(Class clazz,Node oldnode, int count) {
        if (!clazz.equals(Object.class)) {
            Node node = new Node(clazz,count);
            if (map.containsKey(clazz.getSimpleName())) {
                if (oldnode != null) {
                    map.get(clazz.getSimpleName()).addChild(oldnode);
                } else {
                    Node existingNode = map.get(clazz.getSimpleName());
                    existingNode.setCount(existingNode.getCount() + count);
                }
            } else {
                if (oldnode != null) {
                    node.addChild(oldnode);
                }
                map.put(clazz.getSimpleName(),node);
                recursion(clazz.getSuperclass(),node,0);
            }
        }
    }

    private static void correctCounts(Node node) {
        if (node.getChildren()!=null) {
            for (Node node1 : node.getChildren()) {
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
