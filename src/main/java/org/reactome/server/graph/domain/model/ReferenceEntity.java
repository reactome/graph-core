package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceEntity extends DatabaseObject {

    // will be filled together with url
    private String databaseName;
    @ReactomeProperty
    private String identifier;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private List<String> otherIdentifier;
    @ReactomeProperty(addedField = true)
    private String url;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "referenceDatabase")
    private ReferenceDatabase referenceDatabase;

    @Relationship(type = "referenceEntity", direction = Relationship.INCOMING)
    private List<PhysicalEntity> physicalEntity;

    public ReferenceEntity() {}

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getOtherIdentifier() {
        return otherIdentifier;
    }

    public void setOtherIdentifier(List<String> otherIdentifier) {
        this.otherIdentifier = otherIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    @Relationship(type = "crossReference")
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    @Relationship(type = "referenceDatabase")
    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public List<PhysicalEntity> getPhysicalEntity() {
        return physicalEntity;
    }

    @Relationship(type = "referenceDatabase", direction = Relationship.INCOMING)
    public void setPhysicalEntity(List<PhysicalEntity> physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
