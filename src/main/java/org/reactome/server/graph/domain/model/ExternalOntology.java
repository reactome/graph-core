package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ExternalOntology extends DatabaseObject {

    // will be filled together with url
    private String databaseName;
    @ReactomeProperty
    private String definition;
    @ReactomeProperty
    private String identifier;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private List<String> synonym;
    @ReactomeProperty
    private String url;

    @Relationship(type = "instanceOf", direction = Relationship.OUTGOING)
    private List<ExternalOntology> instanceOf;

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
    private ReferenceDatabase referenceDatabase;

    public ExternalOntology() {}

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
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

    public List<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<String> synonym) {
        this.synonym = synonym;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ExternalOntology> getInstanceOf() {
        return instanceOf;
    }

    @Relationship(type = "instanceOf", direction = Relationship.OUTGOING)
    public void setInstanceOf(List<ExternalOntology> instanceOf) {
        this.instanceOf = instanceOf;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }
}
