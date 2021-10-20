package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Set;

/**
 * CatalystActivity describes a instance of biological catalysis. With active units it is possible to specify the exact active component of a complex or set.
 */
@SuppressWarnings("unused")
@Node
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
    @Relationship(type = "catalystActivity", direction = Relationship.Direction.INCOMING)
    private List<ReactionLikeEvent> catalyzedEvent;

    @Relationship(type = "physicalEntity")
    private PhysicalEntity physicalEntity;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    public CatalystActivity() {}

    public CatalystActivity(Long dbId) {
        super(dbId);
    }

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

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

}
