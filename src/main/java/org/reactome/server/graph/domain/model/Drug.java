package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings({"unused", "WeakerAccess"})
@Node
public abstract class Drug extends PhysicalEntity {
    private static final long serialVersionUID = 1L;

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
