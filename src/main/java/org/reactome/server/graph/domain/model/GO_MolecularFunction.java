package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class GO_MolecularFunction extends GO_Term {

    @ReactomeProperty
    private String ecNumber;

    @Relationship(type = "componentOf")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private DatabaseObject componentOf;

    @Relationship(type = "negativelyRegulate")
    private GO_MolecularFunction negativelyRegulate;

    @Relationship(type = "positivelyRegulate")
    private GO_MolecularFunction positivelyRegulate;

    @Relationship(type = "regulate")
    private GO_MolecularFunction regulate;

    public GO_MolecularFunction() {}


    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
    }

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public DatabaseObject getComponentOf() {
        return componentOf;
    }

    @Relationship(type = "componentOf")
    public void setComponentOf(DatabaseObject componentOf) {
        this.componentOf = componentOf;
    }

    public GO_MolecularFunction getNegativelyRegulate() {
        return negativelyRegulate;
    }

    @Relationship(type = "negativelyRegulate")
    public void setNegativelyRegulate(GO_MolecularFunction negativelyRegulate) {
        this.negativelyRegulate = negativelyRegulate;
    }

    public GO_MolecularFunction getPositivelyRegulate() {
        return positivelyRegulate;
    }

    @Relationship(type = "positivelyRegulate")
    public void setPositivelyRegulate(GO_MolecularFunction positivelyRegulate) {
        this.positivelyRegulate = positivelyRegulate;
    }

    public GO_MolecularFunction getRegulate() {
        return regulate;
    }

    @Relationship(type = "regulate")
    public void setRegulate(GO_MolecularFunction regulate) {
        this.regulate = regulate;
    }
}
