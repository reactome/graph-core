package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class GO_MolecularFunction extends DatabaseObject {

    private String accession;
    private String definition;
    private String ecNumber;
    private String name;

    @Relationship(type = "componentOf", direction = Relationship.OUTGOING)
    private DatabaseObject componentOf;

    @Relationship(type = "negativelyRegulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction negativelyRegulate;

    @Relationship(type = "positivelyRegulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction positivelyRegulate;

    @Relationship(type = "referenceDatabase", direction = Relationship.OUTGOING)
    private ReferenceDatabase referenceDatabase;

    @Relationship(type = "regulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction regulate;

    public GO_MolecularFunction() {}

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

    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatabaseObject getComponentOf() {
        return componentOf;
    }

    public void setComponentOf(DatabaseObject componentOf) {
        this.componentOf = componentOf;
    }

    public GO_MolecularFunction getNegativelyRegulate() {
        return negativelyRegulate;
    }

    public void setNegativelyRegulate(GO_MolecularFunction negativelyRegulate) {
        this.negativelyRegulate = negativelyRegulate;
    }

    public GO_MolecularFunction getPositivelyRegulate() {
        return positivelyRegulate;
    }

    public void setPositivelyRegulate(GO_MolecularFunction positivelyRegulate) {
        this.positivelyRegulate = positivelyRegulate;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public GO_MolecularFunction getRegulate() {
        return regulate;
    }

    public void setRegulate(GO_MolecularFunction regulate) {
        this.regulate = regulate;
    }
}
