package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;

public class QueryResultWrapper {
    private DatabaseObject databaseObject;
    private int number;

    public QueryResultWrapper(DatabaseObject databaseObject, int number) {
        this.databaseObject = databaseObject;
        this.number = number;
    }
}
