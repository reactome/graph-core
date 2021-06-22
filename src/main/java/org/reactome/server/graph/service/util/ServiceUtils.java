package org.reactome.server.graph.service.util;

import org.apache.commons.lang3.function.TriFunction;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ServiceUtils {

    public static <R> R fetchById(Object identifier, boolean isLoaded,
                                  Function<String, R> stIdConsumer,
                                  Function<Long, R> dbIdConsumer) {
        R result = null;
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            result = stIdConsumer.apply(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            result = dbIdConsumer.apply(Long.parseLong(id));
        }
        return setIsLoaded(result, isLoaded);
    }

    public static <R, B> R fetchById(Object identifier, B b, boolean isLoaded,
                                     BiFunction<String, B, R> stIdConsumer,
                                     BiFunction<Long, B, R> dbIdConsumer) {
        R result = null;
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            result = stIdConsumer.apply(id, b);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            result = dbIdConsumer.apply(Long.parseLong(id), b);
        }
        return setIsLoaded(result, isLoaded);
    }

    public static <R, B> R fetchById(String identifier, @Nullable B b, boolean isLoaded,
                                     BiFunction<String, B, R> stIdConsumerWithB,
                                     Function<String, R> stIdConsumerWithoutB,
                                     BiFunction<Long, B, R> dbIdConsumerWithB,
                                     Function<Long, R> dbIdConsumerWithoutB) {
        R result = null;
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            result = b != null ? stIdConsumerWithB.apply(id, b) : stIdConsumerWithoutB.apply(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            long dbId = Long.parseLong(id);
            result = b != null ? dbIdConsumerWithB.apply(dbId, b) : dbIdConsumerWithoutB.apply(dbId);
        }
        return setIsLoaded(result, isLoaded);
    }

    public static <R, B, C> R fetchById(Object identifier, B b, C c, boolean isLoaded,
                                        TriFunction<String, B, C, R> stIdConsumer,
                                        TriFunction<Long, B, C, R> dbIdConsumer) {
        R result = null;
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            result = stIdConsumer.apply(id, b, c);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            result = dbIdConsumer.apply(Long.parseLong(id), b, c);
        }
        return setIsLoaded(result, isLoaded);
    }

    private static <T> T setIsLoaded(T object, boolean isLoaded) {
        if (object == null) return null;

        if (DatabaseObject.class.isAssignableFrom(object.getClass())) {
            ((DatabaseObject) object).isLoaded = isLoaded;
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            ((Collection<?>) object).forEach(o -> ServiceUtils.setIsLoaded(o, isLoaded));
        }

        return object;
    }
}
