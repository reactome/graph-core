package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class GO_CellularComponent extends DatabaseObject {

    private String accession;

    private String definition;

    private ReferenceDatabase referenceDatabase;

    private String referenceDatabaseClass;

    public GO_CellularComponent() {
    }

    public String getAccession() {
        return this.accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return this.referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public String getReferenceDatabaseClass() {
        return this.referenceDatabaseClass;
    }

    public void setReferenceDatabaseClass(String referenceDatabaseClass) {
        this.referenceDatabaseClass = referenceDatabaseClass;
    }
}
