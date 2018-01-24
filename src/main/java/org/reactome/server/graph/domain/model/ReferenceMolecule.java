package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

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

    @JsonIgnore
    @ReactomeSchemaIgnore
    public Boolean getTrivial() {
        return trivial;
    }

    public void setTrivial(Boolean trivial) {
        this.trivial = trivial;
    }
}
