package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;

public class QueryResultWrapper {
    private DatabaseObject databaseObject;
    private int stoichiometry;

    public QueryResultWrapper(DatabaseObject databaseObject) {
        this(databaseObject, 1);
    }

    public QueryResultWrapper(DatabaseObject databaseObject, int stoichiometry) {
        this.databaseObject = databaseObject;
        this.stoichiometry = stoichiometry;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public int getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(int stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryResultWrapper)) return false;

        QueryResultWrapper that = (QueryResultWrapper) o;

        if (stoichiometry != that.stoichiometry) return false;
        return databaseObject.equals(that.databaseObject);
    }

    @Override
    public int hashCode() {
        int result = databaseObject.hashCode();
        result = 31 * result + stoichiometry;
        return result;
    }
}
