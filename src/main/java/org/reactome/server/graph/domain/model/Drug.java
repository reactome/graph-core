package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Drug extends PhysicalEntity {

    @Relationship(type = "referenceTherapeutic")
    private ReferenceTherapeutic referenceTherapeutic;

    public Drug() {}

    public ReferenceTherapeutic getReferenceTherapeutic() {
        return referenceTherapeutic;
    }

    @Relationship(type = "referenceTherapeutic")
    public void setReferenceTherapeutic(ReferenceTherapeutic referenceTherapeutic) {
        this.referenceTherapeutic = referenceTherapeutic;
    }
}
