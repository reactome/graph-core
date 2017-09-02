package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;

import java.util.List;
import java.util.Set;

/**
 * CatalystActivity describes a instance of biological catalysis. With active units it is possible to specify the exact active component of a complex or set.
 */
@SuppressWarnings("unused")
@NodeEntity
public class CatalystActivity extends DatabaseObject  {

    @Relationship(type = "activeUnit")
    private Set<PhysicalEntity> activeUnit;

    @Relationship(type = "activity")
    private GO_MolecularFunction activity;

    /**
     * catalyzedEvent is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "catalystActivity", direction = Relationship.INCOMING)
    private List<ReactionLikeEvent> catalyzedEvent;

    @Relationship(type = "physicalEntity")
    private PhysicalEntity physicalEntity;

    @Relationship(type = "regulatedBy")
    private List<Regulation> regulatedBy;

    public CatalystActivity() {}

    public Set<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    @Relationship(type = "activeUnit")
    public void setActiveUnit(Set<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }

    public GO_MolecularFunction getActivity() {
        return activity;
    }

    @Relationship(type = "activity")
    public void setActivity(GO_MolecularFunction activity) {
        this.activity = activity;
    }

    @Relationship(type = "catalystActivity", direction = Relationship.INCOMING)
    public List<ReactionLikeEvent> getCatalyzedEvent() {
        return catalyzedEvent;
    }

    @Relationship(type = "catalystActivity", direction = Relationship.INCOMING)
    public void setCatalyzedEvent(List<ReactionLikeEvent> catalyzedEvent) {
        this.catalyzedEvent = catalyzedEvent;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @Relationship(type = "physicalEntity")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    @Relationship(type = "regulatedBy")
    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
    }
}
