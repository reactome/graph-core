package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class EntityWithAccessionedSequence extends GenomeEncodedEntity {

    private Integer endCoordinate;
    private Integer startCoordinate;

    @Relationship(type = "hasModifiedResidue", direction = Relationship.OUTGOING)
    private List<AbstractModifiedResidue> hasModifiedResidue;

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    private ReferenceSequence referenceEntity;

    public EntityWithAccessionedSequence() {}

    public Integer getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Integer endCoordinate) {
        this.endCoordinate = endCoordinate;
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

    public void setHasModifiedResidue(List<AbstractModifiedResidue> hasModifiedResidue) {
        this.hasModifiedResidue = hasModifiedResidue;
    }

    public ReferenceSequence getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(ReferenceSequence referenceEntity) {
        this.referenceEntity = referenceEntity;
    }
}
