package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeTransient;

import java.util.List;
import java.util.Set;

/**
 * CatalystActivity describes a instance of biological catalysis. With active units it is possible to specify the exact active component of a complex or set.
 */
@SuppressWarnings("unused")
@NodeEntity
public class CatalystActivity extends DatabaseObject  {

    @Relationship(type = "activeUnit", direction = Relationship.OUTGOING)
    private Set<PhysicalEntity> activeUnit;

    @Relationship(type = "activity", direction = Relationship.OUTGOING)
    private GO_MolecularFunction activity;

    @ReactomeTransient
    @Relationship(type = "catalystActivity", direction = Relationship.INCOMING)
    private List<ReactionLikeEvent> catalyzedEvent;

    @Relationship(type = "physicalEntity", direction = Relationship.OUTGOING)
    private PhysicalEntity physicalEntity;

    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    private List<Regulation> regulatedBy;

    public CatalystActivity() {}

    public Set<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(Set<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }

    public GO_MolecularFunction getActivity() {
        return activity;
    }

    public void setActivity(GO_MolecularFunction activity) {
        this.activity = activity;
    }

    public List<ReactionLikeEvent> getCatalyzedEvent() {
        return catalyzedEvent;
    }

    public void setCatalyzedEvent(List<ReactionLikeEvent> catalyzedEvent) {
        this.catalyzedEvent = catalyzedEvent;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
    }
}
