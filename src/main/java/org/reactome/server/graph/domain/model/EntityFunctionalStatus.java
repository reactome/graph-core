package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class EntityFunctionalStatus extends DatabaseObject {

    @Relationship(type = "functionalStatus")
    private List<FunctionalStatus> functionalStatus;

    //Formerly "physicalEntity"
    @Relationship(type = "diseaseEntity")
    private PhysicalEntity diseaseEntity;

    @Relationship(type = "normalEntity")
    private PhysicalEntity normalEntity;

    public EntityFunctionalStatus() {}

    public List<FunctionalStatus> getFunctionalStatus() {
        return functionalStatus;
    }

    @Relationship(type = "functionalStatus")
    public void setFunctionalStatus(List<FunctionalStatus> functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    public PhysicalEntity getDiseaseEntity() {
        return diseaseEntity;
    }

    @Relationship(type = "diseaseEntity")
    public void setDiseaseEntity(PhysicalEntity diseaseEntity) {
        this.diseaseEntity = diseaseEntity;
    }

    public PhysicalEntity getNormalEntity() {
        return normalEntity;
    }

    @Relationship(type = "normalEntity")
    public void setNormalEntity(PhysicalEntity normalEntity) {
        this.normalEntity = normalEntity;
    }
}
