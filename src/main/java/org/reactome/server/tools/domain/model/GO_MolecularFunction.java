package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class GO_MolecularFunction extends GO_Term {

    @ReactomeProperty
    private String ecNumber;

    @Relationship(type = "componentOf", direction = Relationship.OUTGOING)
    private DatabaseObject componentOf;

    @Relationship(type = "negativelyRegulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction negativelyRegulate;

    @Relationship(type = "positivelyRegulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction positivelyRegulate;

    @Relationship(type = "regulate", direction = Relationship.OUTGOING)
    private GO_MolecularFunction regulate;

    public GO_MolecularFunction() {}


    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
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

    public GO_MolecularFunction getRegulate() {
        return regulate;
    }

    public void setRegulate(GO_MolecularFunction regulate) {
        this.regulate = regulate;
    }
}
