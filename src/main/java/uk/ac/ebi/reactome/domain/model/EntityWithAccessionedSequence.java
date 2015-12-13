package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class EntityWithAccessionedSequence extends GenomeEncodedEntity {

    private Integer endCoordinate;

    @Relationship
    private ReferenceSequence referenceEntity;

    private Integer startCoordinate;

    @Relationship
    private List<AbstractModifiedResidue> hasModifiedResidue;
    
    public EntityWithAccessionedSequence() {
    }

    public List<AbstractModifiedResidue> getHasModifiedResidue() {
        return hasModifiedResidue;
    }

    public void setHasModifiedResidue(List<AbstractModifiedResidue> hasModifiedResidue) {
        this.hasModifiedResidue = hasModifiedResidue;
    }

    public Integer getEndCoordinate() {
        return this.endCoordinate;
    }

    public void setEndCoordinate(Integer endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public ReferenceSequence getReferenceEntity() {
        return this.referenceEntity;
    }

    public void setReferenceEntity(ReferenceSequence referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    public Integer getStartCoordinate() {
        return this.startCoordinate;
    }

    public void setStartCoordinate(Integer startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

}
