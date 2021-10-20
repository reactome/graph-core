package org.reactome.server.graph.custom;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.result.CustomQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO for testing the Custom Cypher Queries
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryComplex implements CustomQuery {
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
        return customReferences == null ? customReferences = new ArrayList<>() : customReferences;
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

    @Override
    public CustomQuery build(Record r) {
        this.setStId(r.get("stId").asString());
        this.setDisplayName(r.get("displayName").asString());
        for (Value value : r.get("customReferences").values()) {
            this.getCustomReferences().add(new CustomReference(value.get("database").asString(), value.get("identifier").asString()));
        }
        this.setDatabaseNames(r.get("databaseNames").asList(Value::toString));
        this.setDbIds(r.get("dbIds").asList(Value::asLong));
        return this;
    }
}
