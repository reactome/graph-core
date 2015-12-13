package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class ExternalOntology extends DatabaseObject {
    private String definition;
    private String identifier;
    @Relationship
    private List<ExternalOntology> instanceOf;
    private List<String> name;
    @Relationship
    private ReferenceDatabase referenceDatabase;
    private List<String> synonym;
    
    public ExternalOntology() {
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

    public List<ExternalOntology> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<ExternalOntology> instanceOf) {
        this.instanceOf = instanceOf;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public List<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<String> synonym) {
        this.synonym = synonym;
    }
    
}
