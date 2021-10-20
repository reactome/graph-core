package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;


@SuppressWarnings("unused")
public class SimpleReferenceObject {

    private Long dbId;
    private String identifier;
    private String databaseName;

    public SimpleReferenceObject() {
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public static SimpleReferenceObject build(Record record) {
        SimpleReferenceObject simpleReferenceObject = new SimpleReferenceObject();
        simpleReferenceObject.setDbId(record.get("dbId").asLong());
        simpleReferenceObject.setIdentifier(record.get("identifier").asString());
        simpleReferenceObject.setDatabaseName(record.get("databaseName").asString());
        return simpleReferenceObject;
    }

}
