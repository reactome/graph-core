package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

/**
 * A chemical species not encoded directly or indirectly in the genome, typically small molecules such as ATP or ethanol.
 * The detailed structure of a simpleEntity is specified by linking it to details of the molecule in the ChEBI or KEGG databases via the referenceEntity slot. Use of KEGG is deprecated.
 */
@SuppressWarnings("unused")
@NodeEntity
public class SimpleEntity extends PhysicalEntity {

    @ReactomeProperty
    private String referenceType;

    @Relationship(type = "referetnceEntity", direction = Relationship.OUTGOING)
    private ReferenceMolecule referenceEntity;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    private Species species;

    public SimpleEntity() {}

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

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
