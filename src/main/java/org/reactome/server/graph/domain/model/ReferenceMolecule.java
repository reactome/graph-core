package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
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
