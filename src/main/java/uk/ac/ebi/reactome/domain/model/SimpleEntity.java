package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * ReferenceEntity is single valued (ReactomeDataModel is wrong in this case)
 */
@SuppressWarnings("unused")
@NodeEntity
public class SimpleEntity extends PhysicalEntity {

    @Relationship(type = "referetnceEntity", direction = Relationship.OUTGOING)
    private ReferenceMolecule referenceEntity;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
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
