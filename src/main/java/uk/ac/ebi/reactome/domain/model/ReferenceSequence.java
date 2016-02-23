package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceSequence extends ReferenceEntity {

    private String checksum;
    private List<String> comment;
    private List<String> description;
    private List<String> geneName;
    private Boolean isSequenceChanged;
    private List<String> keyword;
    private List<String> secondaryIdentifier;
    private Integer sequenceLength;
    private Species species;
    private String url;

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

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
