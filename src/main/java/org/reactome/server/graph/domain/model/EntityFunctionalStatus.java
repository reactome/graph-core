package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@Node
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

    public void setFunctionalStatus(List<FunctionalStatus> functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    public PhysicalEntity getDiseaseEntity() {
        return diseaseEntity;
    }

    public void setDiseaseEntity(PhysicalEntity diseaseEntity) {
        this.diseaseEntity = diseaseEntity;
    }

    public PhysicalEntity getNormalEntity() {
        return normalEntity;
    }

    public void setNormalEntity(PhysicalEntity normalEntity) {
        this.normalEntity = normalEntity;
    }
}
