package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class PsiMod extends ExternalOntology {

    @JsonIgnore
    @ReactomeProperty(addedField = true)
    private String abbreviation;

    public PsiMod() {}

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
