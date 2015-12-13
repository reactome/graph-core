package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * GEE contains proteins and nucleic acids with unknown sequences
 */
@NodeEntity
public class GenomeEncodedEntity extends PhysicalEntity {

    @Relationship
    private Taxon species;

    public GenomeEncodedEntity() {
    }

    public Taxon getSpecies() {
        return species;
    }

    public void setSpecies(Taxon species) {
        this.species = species;
    }


}
