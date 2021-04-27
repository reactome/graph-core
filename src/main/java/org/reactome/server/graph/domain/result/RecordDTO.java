package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;

public class RecordDTO {

    private DatabaseObject databaseObject;
    private long n;

    public RecordDTO(DatabaseObject databaseObject, long n) {
        this.databaseObject = databaseObject;
        this.n = n;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public long getN() {
        return n;
    }

    public void setN(long n) {
        this.n = n;
    }
}
