package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceSequence extends ReferenceEntity {

    @ReactomeProperty
    private String checksum;
    @ReactomeProperty
    private List<String> comment;
    @ReactomeProperty
    private List<String> description;
    @ReactomeProperty
    private List<String> geneName;
    @ReactomeProperty
    private Boolean isSequenceChanged;
    @ReactomeProperty
    private List<String> keyword;
    @ReactomeProperty
    private List<String> secondaryIdentifier;
    @ReactomeProperty
    private Integer sequenceLength;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    private Species species;

    public ReferenceSequence() {}

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getGeneName() {
        return geneName;
    }

    public void setGeneName(List<String> geneName) {
        this.geneName = geneName;
    }

    public Boolean getIsSequenceChanged() {
        return isSequenceChanged;
    }

    public void setIsSequenceChanged(Boolean isSequenceChanged) {
        this.isSequenceChanged = isSequenceChanged;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public List<String> getSecondaryIdentifier() {
        return secondaryIdentifier;
    }

    public void setSecondaryIdentifier(List<String> secondaryIdentifier) {
        this.secondaryIdentifier = secondaryIdentifier;
    }

    public Integer getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(Integer sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public Species getSpecies() {
        return species;
    }

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    public void setSpecies(Species species) {
        this.species = species;
    }

}
