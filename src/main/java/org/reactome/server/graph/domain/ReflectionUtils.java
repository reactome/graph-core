package org.reactome.server.graph.domain;

import org.neo4j.driver.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static List<Field> getAllFields(Class<?> type) {
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

    public static void build(Value v, List<Field> fields, Object _clazz) {
        for (Field field : fields) {
            Value va = v.get(field.getName());
            try {
                field.setAccessible(true);
                if (field.getType().isAssignableFrom(String.class)) {
                    field.set(_clazz, va.asString());
                } else if (field.getType().isAssignableFrom(Long.class)) {
                    field.set(_clazz, va.asLong());
                } else if (field.getType().isAssignableFrom(Integer.class) || field.getType().isPrimitive()) {
//                    System.out.println(field.getName());
                    field.set(_clazz, va.asInt(0));
                } else if (field.getType().isAssignableFrom(Boolean.class)) {
                    field.set(_clazz, va.asBoolean(Boolean.FALSE));
                }
            } catch (IllegalAccessException e) {
                System.out.println(field.getName());
            }
        }
    }
}
