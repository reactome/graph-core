package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Taxon extends DatabaseObject {

    @Relationship
    private List<DatabaseIdentifier> crossReference;
    private List<String> name;
    @Relationship
    private Taxon superTaxon;
    
    public Taxon() {
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public Taxon getSuperTaxon() {
        return superTaxon;
    }

    public void setSuperTaxon(Taxon superTaxon) {
        this.superTaxon = superTaxon;
    }
    

}
