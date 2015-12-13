package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class ReferenceEntity extends DatabaseObject {

    @Relationship
    private List<DatabaseIdentifier> crossReference;
    private String identifier;
    private List<String> name;
    private List<String> otherIdentifier;
    @Relationship
    private ReferenceDatabase referenceDatabase;

    public ReferenceEntity() {

    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
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

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }


}
