package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceMolecule extends ReferenceEntity {

    @ReactomeProperty
    private String formula;

    @ReactomeProperty(addedField = true)
    private Boolean trivial;

    public ReferenceMolecule() {}

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getTrivial() {
        return trivial;
    }

    public void setTrivial(Boolean trivial) {
        this.trivial = trivial;
    }
}
