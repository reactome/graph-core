package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class GO_CellularComponent extends DatabaseObject {

    private String accession;
    private String definition;
    private String referenceDatabaseClass;

    @Relationship(type = "referenceDatabase")
    private ReferenceDatabase referenceDatabase;

    public GO_CellularComponent() {}

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getReferenceDatabaseClass() {
        return referenceDatabaseClass;
    }

    public void setReferenceDatabaseClass(String referenceDatabaseClass) {
        this.referenceDatabaseClass = referenceDatabaseClass;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }
}
