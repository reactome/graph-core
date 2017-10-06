package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

import java.util.List;

/**
 * A protein, RNA, or DNA molecule or fragment thereof in a specified cellular compartment and specific post-translational state. Must be linked to an external database reference, given as the value of referenceSequence. An EWAS typically corresponds to the entire protein or polynucleotide described in the external database. Fragments are defined by setting the first and last residue using the numbering scheme of the external database, entered as startCoordinate and endCoordinate values. Values of 1 and -1 respectively indicate that the true start and end are unconfirmed. EWAS instances are specific to a subcellular compartment; if the same molecule is found in two cellular components it will have two EWASes. EWAS instances by default define an unmodified protein sequence, any post-translational modification (PTM), such as phosphorylation, requires a new EWAS instance. The location and type of any PTM are defined in the hasModifiedResidue slot
 */
@SuppressWarnings("unused")
@NodeEntity
public class EntityWithAccessionedSequence extends GenomeEncodedEntity {

    @ReactomeProperty
    private Integer endCoordinate;
    @ReactomeProperty(addedField = true)
    private String referenceType;
    @ReactomeProperty
    private Integer startCoordinate;

    @Relationship(type = "hasModifiedResidue")
    private List<AbstractModifiedResidue> hasModifiedResidue;

    @Relationship(type = "referenceEntity")
    private ReferenceSequence referenceEntity;

    public EntityWithAccessionedSequence() {}

    public Integer getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Integer endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Integer startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public List<AbstractModifiedResidue> getHasModifiedResidue() {
        return hasModifiedResidue;
    }

    @Relationship(type = "hasModifiedResidue")
    public void setHasModifiedResidue(List<AbstractModifiedResidue> hasModifiedResidue) {
        this.hasModifiedResidue = hasModifiedResidue;
    }

    public ReferenceSequence getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceSequence referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        switch (referenceType) {
            case ("ReferenceGeneProduct"):
                return "Protein";
            case ("ReferenceDNASequence"):
                return "DNA Sequence";
            case ("ReferenceRNASequence"):
                return "RNA Sequence";
            default:
                return super.getClassName();
        }
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A protein, RNA, or DNA molecule or fragment thereof in a specified cellular compartment and specific post-translational state. " +
                "Must be linked to an external database reference, given as the value of referenceSequence. An EWAS typically corresponds to the entire protein or polynucleotide described in the external database. " +
                "Fragments are defined by setting the first and last residue using the numbering scheme of the external database, entered as startCoordinate and endCoordinate values. Values of 1 and -1 respectively indicate that the true start and end are unconfirmed. " +
                "EWAS instances are specific to a subcellular compartment; if the same molecule is found in two cellular components it will have two EWASes. " +
                "EWAS instances by default define an unmodified protein sequence, any post-translational modification (PTM), such as phosphorylation, requires a new EWAS instance. " +
                "The location and type of any PTM are defined in the hasModifiedResidue slot";
    }
}
