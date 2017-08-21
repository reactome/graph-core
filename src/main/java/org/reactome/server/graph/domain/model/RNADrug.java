package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@NodeEntity
public class RNADrug extends Drug {

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    private ReferenceRNASequence referenceEntity;

    public RNADrug() {

    }

    public ReferenceRNASequence getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity", direction = Relationship.OUTGOING)
    public void setReferenceEntity(ReferenceRNASequence referenceEntity) {
        this.referenceEntity = referenceEntity;
    }
}
