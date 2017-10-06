package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * A chemical species not encoded directly or indirectly in the genome, typically small molecules such as ATP or ethanol.
 * The detailed structure of a simpleEntity is specified by linking it to details of the molecule in the ChEBI or KEGG databases via the referenceEntity slot. Use of KEGG is deprecated.
 */
@SuppressWarnings("unused")
@NodeEntity
public class SimpleEntity extends PhysicalEntity {

    @ReactomeProperty(addedField = true)
    private String referenceType;

    @Relationship(type = "referenceEntity")
    private ReferenceMolecule referenceEntity;

    @Relationship(type = "species")
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

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceMolecule referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    public Species getSpecies() {
        return species;
    }

    @Relationship(type = "species")
    public void setSpecies(Species species) {
        this.species = species;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A chemical species not encoded directly or indirectly in the genome, typically small molecules such as ATP or ethanol. " +
                "The detailed structure of a simpleEntity is specified by linking it to details of the molecule in the ChEBI or KEGG databases via the referenceEntity slot. Use of KEGG is deprecated";
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Chemical Compound";
    }
}
