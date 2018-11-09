package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Drug extends PhysicalEntity {

    // For backward compatibility. However, the actual type should be ReferenceTherapeutic.
    @Relationship(type = "referenceEntity")
    private ReferenceEntity referenceEntity;

    @Relationship(type = "drugType")
    private DrugType drugType;

    @Relationship(type = "referenceTherapeutic")
    private ReferenceTherapeutic referenceTherapeutic;

    public Drug() {}

    public DrugType getDrugType() {
        return drugType;
    }

    @Relationship(type = "drugType")
    public void setDrugType(DrugType drugType) {
        this.drugType = drugType;
    }

    public ReferenceEntity getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceEntity referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    public ReferenceTherapeutic getReferenceTherapeutic() {
        return referenceTherapeutic;
    }

    @Relationship(type = "referenceTherapeutic")
    public void setReferenceTherapeutic(ReferenceTherapeutic referenceTherapeutic) {
        this.referenceTherapeutic = referenceTherapeutic;
    }
}
