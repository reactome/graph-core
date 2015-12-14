package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class SimpleEntity extends PhysicalEntity {

    /**
     * Because a bug in the data model, this attribute is multiple valued.
     * However, it should be single valued.
     */
    @Relationship(type = "referetnceEntity")
    private ReferenceMolecule referenceEntity;

    @Relationship(type = "species")
    private Species species;

    public SimpleEntity() {}

    public ReferenceMolecule getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(ReferenceMolecule referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

}
