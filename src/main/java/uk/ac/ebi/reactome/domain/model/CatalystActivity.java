package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * CatalystActivity describes a instance of biological catalysis. With active units it is possible to specify the
 * exact active component of a complex or set.
 */
@NodeEntity
public class CatalystActivity extends DatabaseObject {

    @Relationship(type = "HAS_CATALYST", direction = Relationship.OUTGOING)
    private PhysicalEntity physicalEntity;

    @Relationship(type = "HAS_ACTIVE_UNITS", direction = Relationship.OUTGOING)
    private Set<PhysicalEntity> activeUnits;

    @Relationship(type = "IS_REGULATED", direction = Relationship.INCOMING)
    private Regulation regulation;

    public CatalystActivity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @SuppressWarnings("unused")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public Set<PhysicalEntity> getActiveUnits() {
        return activeUnits;
    }

    @SuppressWarnings("unused")
    public void setActiveUnits(Set<PhysicalEntity> activeUnits) {
        this.activeUnits = activeUnits;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    @SuppressWarnings("unused")
    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }
}
