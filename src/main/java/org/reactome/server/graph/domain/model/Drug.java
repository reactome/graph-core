package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Drug extends PhysicalEntity {

    @Relationship(type = "referenceEntity")
    private ReferenceTherapeutic referenceEntity;

    public Drug() {}

    public ReferenceTherapeutic getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceTherapeutic referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

}
