package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeRelationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@Node
public abstract class ReferenceEntity extends DatabaseObject {

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

    @ReactomeRelationship
    @Relationship(type = "referenceEntity", direction = Relationship.Direction.INCOMING)
    private List<PhysicalEntity> physicalEntity;

    public ReferenceEntity() {}

    public String getDatabaseName() {
        return databaseName;
    }

    @ReactomeSchemaIgnore
    @JsonIgnore
    public String getSimplifiedDatabaseName() {
        return databaseName.replace(" ", "-");
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

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public List<PhysicalEntity> getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(List<PhysicalEntity> physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
