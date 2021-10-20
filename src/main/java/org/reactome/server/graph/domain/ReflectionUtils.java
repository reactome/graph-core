package org.reactome.server.graph.domain;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Function;

public class ReflectionUtils {

    public static <T> T build(T t, Record record) {
        List<Field> fields = ReflectionUtils.getAllFields(t.getClass());
        ReflectionUtils.build(record, fields, t);
        return t;
    }

    public static <T> T build(T t, Value value) {
        List<Field> fields = ReflectionUtils.getAllFields(t.getClass());
        ReflectionUtils.build(value, fields, t);
        return t;
    }

    /**
     * Depending on how you write the query and your return statement.
     * You might have a Record or @see build(Value value)
     */
    private static <T> void build(Value v, List<Field> fields, T _instance) {
        for (Field field : fields) {
            Value value = v.get(field.getName());
            setFields(value, field, _instance);
        }
    }

    /**
     * Depending on how you write the query and your return statement.
     * You might have a Record or @see build(Value value)
     */
    private static <T> void build(Record record, List<Field> fields, T _instance) {
        for (Field field : fields) {
            Value value = record.get(field.getName());
            setFields(value, field, _instance);
        }
    }

    private static List<Field> getAllFields(Class<?> type) {
        List<Field> allFields = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            allFields.add(field);
        }
        if (type.getSuperclass() != null) {
            allFields.addAll(getAllFields(type.getSuperclass()));
        }
        return allFields;
    }

    private static void setFields(Value value, Field field, Object _instance) {
        try {
            field.setAccessible(true);
            if (field.getType().isAssignableFrom(String.class)) {
                field.set(_instance, value.asString(null));
            } else if (field.getType().isAssignableFrom(Long.class)) {
                field.set(_instance, value.asLong(0));
            } else if (field.getType().isAssignableFrom(Integer.class)) {
                field.set(_instance, value.asInt(0));
            } else if (field.getType().isAssignableFrom(Double.class)) {
                field.set(_instance, value.asDouble(0));
            } else if (field.getType().isAssignableFrom(Boolean.class)) {
                field.set(_instance, value.asBoolean(Boolean.FALSE));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                if (!value.isNull() && !value.isEmpty()) {
                    List<?> list = value.asList(getCollectionValueType(field));
                    if (List.class.isAssignableFrom(field.getType())) {
                        field.set(_instance, list);
                    } else if (Set.class.isAssignableFrom(field.getType())) {
                        if (SortedSet.class.isAssignableFrom(field.getType())) {
                            field.set(_instance, new TreeSet<>(list));
                        } else {
                            field.set(_instance, new HashSet<>(list));
                        }
                    }
                } else {
                    if (List.class.isAssignableFrom(field.getType())) {
                        field.set(_instance, new ArrayList<>());
                    } else if (Set.class.isAssignableFrom(field.getType())) {
                        if (SortedSet.class.isAssignableFrom(field.getType())) {
                            field.set(_instance, new TreeSet<>());
                        } else {
                            field.set(_instance, new HashSet<>());
                        }
                    }
                }
            } else if (field.getType().isPrimitive()) {
                throw new IllegalAccessException("[" + field.getType() + "] primitive type is not support. Use the wrapper class.");
            }
        } catch (IllegalAccessException e) {
            System.err.println(field.getName());
        }
    }

    private static Function<Value, ?> getCollectionValueType(@NonNull Field field) throws IllegalAccessException {
        Class<?> eType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        if (String.class.isAssignableFrom(eType)) {
            return v -> v.asString(null);
        } else if (Long.class.isAssignableFrom(eType)) {
            return Value::asLong;
        } else if (Integer.class.isAssignableFrom(eType)) {
            return Value::asInt;
        } else if (Double.class.isAssignableFrom(eType)) {
            return Value::asDouble;
        } else if (Boolean.class.isAssignableFrom(eType)) {
            return Value::asBoolean;
        }
        throw new IllegalAccessException(field.getName() + " is a collection of non supported elements: " + eType.toGenericString() + ". Use the wrapper class");
    }

//    /**
//     * Method that uses Java Reflection in order to set the attributes of a specific instance.
//     * The attribute is handled in different ways depends on its type.
//     * The types are: Array of primitive, Collection of [String, Number, primitives and CustomObjects] and Custom Objects.
//     * A external type convert is used to convert the Result "stringObjectMap" once Neo4j returns
//     * an array of Integer for a list of number and we must be able to convert it to whatever type the final user
//     * has defined in his Custom Object. Obviously the type conversion is going to check the type compatibility.
//     * For that we use the jodd.TypeConverter and we are using Java Reflection for the Custom Objects - we manually instantiate
//     * them and recursively call the result list.
//     */
//    private static <T> void setFields(T instance, Field field, Map<String, Object> stringObjectMap) throws Exception {
//        String fieldName = field.getName();
//        Object object = stringObjectMap.get(fieldName);
//        if (object != null) {
//            field.setAccessible(true);
//
//            // An array of primitives do not box automatically then we cast it manually using ArrayUtils and the attribute type
//            if (field.getType().isArray() && field.getType().getComponentType().isPrimitive()) {
//                field.set(instance, toPrimitiveArray(object, field.getType()));
//            } else if (Collection.class.isAssignableFrom(field.getType())) {
//                //The returned results are normally stored in an Array, then we need to convert in case the attribute is a Collection
//                ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
//                Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
//
//                // Parametrised type is String and the convertToCollection is able to convert that
//                if (stringListClass.isAssignableFrom(String.class) || Number.class.isAssignableFrom(stringListClass) || stringListClass.isPrimitive()) {
//                    //noinspection unchecked
//                    field.set(instance, TypeConverterManager.get().convertToCollection(object, (Class<? extends Collection>) field.getType(), stringListClass));
//                } else {
//                    // Parametrised type is a Custom Object so we have to create the list, create the objects
//                    // add them into the list and set attribute in the main class.
//                    Collection<T> customCollection;
//                    if (field.getType().isAssignableFrom(List.class)) {
//                        customCollection = new ArrayList<>();
//                    } else if (field.getType().isAssignableFrom(Set.class)) {
//                        customCollection = new HashSet<>();
//                    } else {
//                        throw new Exception("Couldn't get the class name of the given collection [" + field.getType() + "]");
//                    }
//
//                    if (object instanceof Map[]) {
//                        // The object returned by Neo4j is an array of LinkedHashMap.
//                        LinkedHashMap<String, Object>[] allLinkedHashMap = (LinkedHashMap<String, Object>[]) object;
//                        Collection<Field> fields = getAllFields(stringListClass).values();
//                        for (LinkedHashMap<String, Object> entry : allLinkedHashMap) {
//                            T customInstance = createAndPopulateObject(stringListClass, fields, entry);
//                            customCollection.add(customInstance);
//                        }
//                    }
//                    // set the list in the main class
//                    field.set(instance, customCollection);
//                }
//            } else if (field.getType().isAssignableFrom(String.class) || Number.class.isAssignableFrom(field.getType()) || field.getType().isArray()) {
//                try {
//                    // The attribute is String, Number or an array we know how to convert
//                    field.set(instance, TypeConverterManager.get().convertType(object, field.getType()));
//                } catch (Exception ex) {
//                    field.set(instance, null);
//                }
//            } else {
//                // The attribute is a Custom Object that needs to be instantiated.
//                Collection<Field> customFields = getAllFields(field.getType()).values();
//                T customInstance = createAndPopulateObject(field.getType(), customFields, (Map<String, Object>) object);
//                field.set(instance, customInstance);
//            }
//        }
//    }
//
//    /**
//     * This method is called in the setFields method which calls recursively in order to create and populate the objects themselves.
//     *
//     * @param clazz        the class to be instantiate
//     * @param customFields attributes of given class
//     * @param object       result map of the cypher query, key=String(name of attribute) value=object result of the given attribute
//     * @throws Exception in case we can't create new instances
//     */
//    private static <T> T createAndPopulateObject(Class<?> clazz, Collection<Field> customFields, Map<String, Object> object) throws Exception {
//        T customInstance = (T) clazz.newInstance();
//        for (Field customField : customFields) {
//            setFields(customInstance, customField, object);
//        }
//        return customInstance;
//    }
//
//    /**
//     * Neo4j results always return the Object (wrapper) as an Array (if it is collection). However if we are mapping
//     * an object which attribute is a int[] e.g then it does not 'boxing', then this method checks the type and
//     * return the proper Array of primitive
//     */
//    private static Object toPrimitiveArray(Object value, Class type) {
//        if (type == byte[].class) {
//            return ArrayUtils.toPrimitive((Byte[]) value);
//        } else if (type == short[].class) {
//            return ArrayUtils.toPrimitive((Short[]) value);
//        } else if (type == int[].class) {
//            return ArrayUtils.toPrimitive((Integer[]) value);
//        } else if (type == float[].class) {
//            return ArrayUtils.toPrimitive((Float[]) value);
//        } else if (type == double[].class) {
//            return ArrayUtils.toPrimitive((Double[]) value);
//        } else if (type == char[].class) {
//            return ArrayUtils.toPrimitive((Character[]) value);
//        } else if (type == long[].class) {
//            return ArrayUtils.toPrimitive((Long[]) value);
//        } else if (type == boolean[].class) {
//            return ArrayUtils.toPrimitive((Boolean[]) value);
//        }
//        // version 3.5 we can perform a single call like - return ArrayUtils.toPrimitive(value);
//        return null;
//    }

}
