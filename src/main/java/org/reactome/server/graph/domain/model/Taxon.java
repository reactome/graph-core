package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@Node
public class Taxon extends DatabaseObject {

    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty(addedField = true)
    private String taxId;

    @Deprecated
    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "superTaxon")
    private Taxon superTaxon;
    
    public Taxon() {}

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Deprecated
    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    @Deprecated
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public Taxon getSuperTaxon() {
        return superTaxon;
    }

    public void setSuperTaxon(Taxon superTaxon) {
        this.superTaxon = superTaxon;
    }
}
