package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class StableIdentifier extends DatabaseObject {

    private String identifier;
    private String identifierVersion;
    private Integer referenceDatabase;
    private String referenceDatabaseClass;

    public StableIdentifier() {}

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierVersion() {
        return this.identifierVersion;
    }

    public void setIdentifierVersion(String identifierVersion) {
        this.identifierVersion = identifierVersion;
    }

    public Integer getReferenceDatabase() {
        return this.referenceDatabase;
    }

    public void setReferenceDatabase(Integer referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public String getReferenceDatabaseClass() {
        return this.referenceDatabaseClass;
    }

    public void setReferenceDatabaseClass(String referenceDatabaseClass) {
        this.referenceDatabaseClass = referenceDatabaseClass;
    }

}
