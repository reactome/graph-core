package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public class Drug extends PhysicalEntity {

    @Relationship(type = "referenceEntity")
    private ReferenceTherapeutic referenceEntity;

    @Relationship(type = "drugType")
    private DrugType drugType;

    public Drug() {}

    public DrugType getDrugType() {
        return drugType;
    }

    @Relationship(type = "drugType")
    public void setDrugType(DrugType drugType) {
        this.drugType = drugType;
    }

    public ReferenceTherapeutic getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceTherapeutic referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

}
