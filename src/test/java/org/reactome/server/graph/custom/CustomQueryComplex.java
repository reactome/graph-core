package org.reactome.server.graph.custom;

import java.util.List;

/**
 * POJO for testing the Custom Cypher Queries
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryComplex {

    private String stId;
    private String displayName;
    private List<CustomReference> customReferences;
    private List<Long> dbIds;
    private List<String> databaseNames;


    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<CustomReference> getCustomReferences() {
        return customReferences;
    }

    public void setCustomReferences(List<CustomReference> customReferences) {
        this.customReferences = customReferences;
    }

    public List<Long> getDbIds() {
        return dbIds;
    }

    public void setDbIds(List<Long> dbIds) {
        this.dbIds = dbIds;
    }

    public List<String> getDatabaseNames() {
        return databaseNames;
    }

    public void setDatabaseNames(List<String> databaseNames) {
        this.databaseNames = databaseNames;
    }
}
