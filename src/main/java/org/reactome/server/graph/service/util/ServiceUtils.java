package org.reactome.server.graph.service.util;

import org.apache.commons.lang3.function.TriFunction;
import org.springframework.lang.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ServiceUtils {

    public static <R> R fetchById(Object identifier, Function<String, R> stIdConsumer, Function<Long, R> dbIdConsumer) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return stIdConsumer.apply(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return dbIdConsumer.apply(Long.parseLong(id));
        }
        return null;
    }

    public static <R, B> R fetchByIdAndB(Object identifier, B b, BiFunction<String, B, R> stIdConsumer, BiFunction<Long, B, R> dbIdConsumer) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return stIdConsumer.apply(id, b);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return dbIdConsumer.apply(Long.parseLong(id), b);
        }
        return null;
    }

    public static <R, B> R fetchByIdAndOptionalB(Object identifier, @Nullable B b,
                                                 BiFunction<String, B, R> stIdConsumerWithB,
                                                 Function<String, R> stIdConsumerWithoutB,
                                                 BiFunction<Long, B, R> dbIdConsumerWithB,
                                                 Function<Long, R> dbIdConsumerWithoutB) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return b != null ? stIdConsumerWithB.apply(id, b) : stIdConsumerWithoutB.apply(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            long dbId = Long.parseLong(id);
            return b != null ? dbIdConsumerWithB.apply(dbId, b) : dbIdConsumerWithoutB.apply(dbId);
        }
        return null;
    }

    public static <R, B, C> R fetchByIdAndBAndC(Object identifier, B b, C c, TriFunction<String, B, C, R> stIdConsumer, TriFunction<Long, B, C, R> dbIdConsumer) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return stIdConsumer.apply(id, b, c);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return dbIdConsumer.apply(Long.parseLong(id), b, c);
        }
        return null;
    }
}
