package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings({"unused", "WeakerAccess"})
@Node
public abstract class Drug extends PhysicalEntity {

    @Relationship(type = "referenceEntity")
    private ReferenceTherapeutic referenceEntity;

    public Drug() {}

    public ReferenceTherapeutic getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(ReferenceTherapeutic referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

}
