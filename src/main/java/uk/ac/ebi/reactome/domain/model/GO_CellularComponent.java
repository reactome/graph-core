package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class GO_CellularComponent extends DatabaseObject {

    @ReactomeProperty
    private String accession;
    @ReactomeProperty
    private String definition;

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
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

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }
}
