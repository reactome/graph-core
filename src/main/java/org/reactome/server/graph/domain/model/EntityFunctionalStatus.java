package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class EntityFunctionalStatus extends DatabaseObject {

    @Relationship(type = "functionalStatus", direction = Relationship.OUTGOING)
    private List<FunctionalStatus> functionalStatus;

    @Relationship(type = "physicalEntity", direction = Relationship.OUTGOING)
    private PhysicalEntity physicalEntity;
    
    public EntityFunctionalStatus() {}

    public List<FunctionalStatus> getFunctionalStatus() {
        return functionalStatus;
    }

    @Relationship(type = "functionalStatus", direction = Relationship.OUTGOING)
    public void setFunctionalStatus(List<FunctionalStatus> functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @Relationship(type = "physicalEntity", direction = Relationship.OUTGOING)
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
