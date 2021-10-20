package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class SimpleDatabaseObject implements DatabaseObjectLike {

    private Long dbId;
    private String stId;
    private String displayName;
    private String schemaClass;

    public SimpleDatabaseObject() {}

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

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

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setLabels(Collection<String> labels) {
        this.schemaClass = DatabaseObjectUtils.getSchemaClass(labels);
    }

    public static SimpleDatabaseObject build(Record record) {
        SimpleDatabaseObject simpleDatabaseObject = new SimpleDatabaseObject();
        simpleDatabaseObject.setDbId(record.get("dbId").asLong());
        simpleDatabaseObject.setStId(record.get("stId").asString(null));
        simpleDatabaseObject.setDisplayName(record.get("displayName").asString());
        simpleDatabaseObject.setLabels(record.get("labels").asList(Value::asString));
        return simpleDatabaseObject;
    }

    public static SimpleDatabaseObject build(Value value) {
        SimpleDatabaseObject simpleDatabaseObject = new SimpleDatabaseObject();
        simpleDatabaseObject.setDbId(value.get("dbId").asLong());
        simpleDatabaseObject.setStId(value.get("stId").asString(null));
        simpleDatabaseObject.setDisplayName(value.get("displayName").asString());
        if(!value.get("labels").isNull()) {
            simpleDatabaseObject.setLabels(value.get("labels").asList(Value::asString));
        } else {
            simpleDatabaseObject.setSchemaClass(value.get("schemaClass").asString());
        }
        return simpleDatabaseObject;
    }

    private void setSchemaClass(String schemaClass) {
        this.schemaClass = schemaClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDatabaseObject that = (SimpleDatabaseObject) o;

        return Objects.equals(dbId, that.dbId);
    }

    @Override
    public int hashCode() {
        return dbId != null ? dbId.hashCode() : 0;
    }
}
