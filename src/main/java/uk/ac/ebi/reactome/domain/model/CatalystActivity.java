package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Set;

/**
 * CatalystActivity describes a instance of biological catalysis. With active units it is possible to specify the
 * exact active component of a complex or set.
 */
@NodeEntity
public class CatalystActivity extends DatabaseObject  { // implements Regulator

//    private String physicalEntityClass;

    @Relationship(type = "activity")
    private GO_MolecularFunction activity;

    @Relationship(type = "physicalEntity")
    private PhysicalEntity physicalEntity;

    @Relationship(type = "activeUnit")
    private Set<PhysicalEntity> activeUnit;

    /**
     * Regulation related attributes
     */
    @Relationship(type = "regulatedBy")
    private List<Regulation> regulatedBy;

    public CatalystActivity() {}

    public GO_MolecularFunction getActivity() {
        return activity;
    }

    public void setActivity(GO_MolecularFunction activity) {
        this.activity = activity;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public Set<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(Set<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }

    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
    }
}
