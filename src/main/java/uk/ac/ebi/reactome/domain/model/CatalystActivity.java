package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

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
    private List<PhysicalEntity> activeUnit;

    public CatalystActivity() {}

//    public String getPhysicalEntityClass() {
//        return physicalEntityClass;
//    }
//
//    public void setPhysicalEntityClass(String physicalEntityClass) {
//        this.physicalEntityClass = physicalEntityClass;
//    }

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

    public List<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(List<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }
}
