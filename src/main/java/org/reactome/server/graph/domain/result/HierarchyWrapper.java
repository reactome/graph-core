package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;

import java.util.Collection;

public class HierarchyWrapper {

    private DatabaseObject databaseObject;
    private Collection<Collection<PathwayResult>> nodes;

    public HierarchyWrapper(DatabaseObject databaseObject, Collection<Collection<PathwayResult>> nodes) {
        this.databaseObject = databaseObject;
        this.nodes = nodes;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    public void setDatabaseObject(DatabaseObject databaseObject) {
        this.databaseObject = databaseObject;
    }

    public Collection<Collection<PathwayResult>> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Collection<PathwayResult>> nodes) {
        this.nodes = nodes;
    }
}

