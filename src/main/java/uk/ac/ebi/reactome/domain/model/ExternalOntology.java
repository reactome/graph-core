package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ExternalOntology extends DatabaseObject {

    private String definition;
    private String identifier;
    private List<String> name;
    private List<String> synonym;

    @Relationship(type = "instanceOf", direction = Relationship.OUTGOING)
    private List<ExternalOntology> instanceOf;

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
    private ReferenceDatabase referenceDatabase;

    public ExternalOntology() {}

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

    public List<ExternalOntology> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<ExternalOntology> instanceOf) {
        this.instanceOf = instanceOf;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }
}
