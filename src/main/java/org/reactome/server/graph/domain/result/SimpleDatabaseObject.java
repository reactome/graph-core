package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QueryResult
public class SimpleDatabaseObject {

    private Long dbId;

    private String stableIdentifier;

    private String displayName;

    private String schemaClass;

    public SimpleDatabaseObject() {}

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getStableIdentifier() {
        return stableIdentifier;
    }

    public void setStableIdentifier(String stableIdentifier) {
        this.stableIdentifier = stableIdentifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setLabels(Collection<String> labels) {
        try {
            this.schemaClass = DatabaseObjectUtils.getSchemaClass(labels);
        } catch (ClassNotFoundException e) {
            //Nothing here
        }
    }
}
