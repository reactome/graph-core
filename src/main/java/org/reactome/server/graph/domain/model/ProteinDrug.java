package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@NodeEntity
public class ProteinDrug extends Drug {

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    private ReferenceGeneProduct referenceEntity;

    public ProteinDrug() {

    }

    public ReferenceGeneProduct getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    public void setReferenceEntity(ReferenceGeneProduct referenceEntity) {
        this.referenceEntity = referenceEntity;
    }
}
