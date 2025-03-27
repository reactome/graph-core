package org.reactome.server.graph.util;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.mapping.callback.BeforeBindCallback;

@Configuration
public class TestConfiguration implements BeforeBindCallback<DatabaseObject> {

    private long idGenerator = 0;

    @Override
    public DatabaseObject onBeforeBind(DatabaseObject entity) {
        long dbId = --idGenerator;
        String stId = "R-TST-" + (-1 * dbId);
        entity.setStId(stId);
        entity.setDbId(dbId);
        return entity;
    }
}
