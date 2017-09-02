package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@NodeEntity
public class ChemicalDrug extends Drug {

    @Relationship(type = "referenceEntity")
    private ReferenceMolecule referenceEntity;

    public ChemicalDrug() {

    }

    public ReferenceMolecule getReferenceEntity() {
        return referenceEntity;
    }

    @Relationship(type = "referenceEntity")
    public void setReferenceEntity(ReferenceMolecule referenceEntity) {
        this.referenceEntity = referenceEntity;
    }
}
