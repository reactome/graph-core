package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class GO_MolecularFunction extends GO_Term {

    @ReactomeProperty
    private String ecNumber;

    @Relationship(type = "componentOf")
    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    private List<GO_Term> componentOf;

    @Relationship(type = "hasPart")
    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    private List<GO_Term> hasPart;

    @Relationship(type = "instanceOf")
    private List<GO_MolecularFunction> instanceOf;

    @Relationship(type = "negativelyRegulate")
    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    private List<GO_Term> negativelyRegulate;

    @Relationship(type = "positivelyRegulate")
    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    private List<GO_Term> positivelyRegulate;

    @Relationship(type = "regulate")
    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    private List<GO_Term> regulate;

    public GO_MolecularFunction() {}


    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
    }

    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    public List<GO_Term> getComponentOf() {
        return componentOf;
    }

    @Relationship(type = "componentOf")
    public void setComponentOf(List<GO_Term> componentOf) {
        this.componentOf = componentOf;
    }

    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    public List<GO_Term> getHasPart() {
        return hasPart;
    }

    @Relationship(type = "hasPart")
    public void setHasPart(List<GO_Term> hasPart) {
        this.hasPart = hasPart;
    }

    public List<GO_MolecularFunction> getInstanceOf() {
        return instanceOf;
    }

    @Relationship(type = "instanceOf")
    public void setInstanceOf(List<GO_MolecularFunction> instanceOf) {
        this.instanceOf = instanceOf;
    }

    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    public List<GO_Term> getNegativelyRegulate() {
        return negativelyRegulate;
    }

    @Relationship(type = "negativelyRegulate")
    public void setNegativelyRegulate(List<GO_Term> negativelyRegulate) {
        this.negativelyRegulate = negativelyRegulate;
    }

    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    public List<GO_Term> getPositivelyRegulate() {
        return positivelyRegulate;
    }

    @Relationship(type = "positivelyRegulate")
    public void setPositivelyRegulate(List<GO_Term> positivelyRegulate) {
        this.positivelyRegulate = positivelyRegulate;
    }

    @ReactomeAllowedClasses(allowed = {GO_BiologicalProcess.class, GO_MolecularFunction.class})
    public List<GO_Term> getRegulate() {
        return regulate;
    }

    @Relationship(type = "regulate")
    public void setRegulate(List<GO_Term> regulate) {
        this.regulate = regulate;
    }
}
