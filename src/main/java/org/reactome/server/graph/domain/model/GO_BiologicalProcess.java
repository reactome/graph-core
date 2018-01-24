package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class GO_BiologicalProcess extends GO_Term{

    @Relationship(type = "componentOf")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private List<GO_Term> componentOf;

    @Relationship(type = "hasPart")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private List<GO_Term> hasPart;

    @Relationship(type = "instanceOf")
    private List<GO_BiologicalProcess> instanceOf;

    @Relationship(type = "negativelyRegulate")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private List<GO_Term> negativelyRegulate;

    @Relationship(type = "positivelyRegulate")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private List<GO_Term> positivelyRegulate;

    @Relationship(type = "regulate")
    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    private List<GO_Term> regulate;

    public GO_BiologicalProcess() {}

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public List<GO_Term> getComponentOf() {
        return componentOf;
    }

    @Relationship(type = "componentOf")
    public void setComponentOf(List<GO_Term> componentOf) {
        this.componentOf = componentOf;
    }

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public List<GO_Term> getHasPart() {
        return hasPart;
    }

    @Relationship(type = "hasPart")
    public void setHasPart(List<GO_Term> hasPart) {
        this.hasPart = hasPart;
    }

    public List<GO_BiologicalProcess> getInstanceOf() {
        return instanceOf;
    }

    @Relationship(type = "instanceOf")
    public void setInstanceOf(List<GO_BiologicalProcess> instanceOf) {
        this.instanceOf = instanceOf;
    }

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public List<GO_Term> getNegativelyRegulate() {
        return negativelyRegulate;
    }

    @Relationship(type = "negativelyRegulate")
    public void setNegativelyRegulate(List<GO_Term> negativelyRegulate) {
        this.negativelyRegulate = negativelyRegulate;
    }

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public List<GO_Term> getPositivelyRegulate() {
        return positivelyRegulate;
    }

    @Relationship(type = "positivelyRegulate")
    public void setPositivelyRegulate(List<GO_Term> positivelyRegulate) {
        this.positivelyRegulate = positivelyRegulate;
    }

    @ReactomeAllowedClasses(allowed = {GO_MolecularFunction.class, GO_BiologicalProcess.class})
    public List<GO_Term> getRegulate() {
        return regulate;
    }

    @Relationship(type = "regulate")
    public void setRegulate(List<GO_Term> regulate) {
        this.regulate = regulate;
    }
}
