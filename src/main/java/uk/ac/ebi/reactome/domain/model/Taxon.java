package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Taxon extends DatabaseObject {

    private List<String> name;

    @Relationship
    private List<DatabaseIdentifier> crossReference;

    @Relationship
    private Taxon superTaxon;
    
    public Taxon() {}

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

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
