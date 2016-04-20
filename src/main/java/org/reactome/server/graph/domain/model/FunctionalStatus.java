package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class FunctionalStatus extends DatabaseObject {

    @Relationship(type = "functionalStatusType", direction = Relationship.OUTGOING)
    private FunctionalStatusType functionalStatusType;

    @Relationship(type = "structuralVariant", direction = Relationship.OUTGOING)
    private SequenceOntology structuralVariant;
    
    public FunctionalStatus() {}

    public FunctionalStatusType getFunctionalStatusType() {
        return functionalStatusType;
    }

    public void setFunctionalStatusType(FunctionalStatusType functionalStatusType) {
        this.functionalStatusType = functionalStatusType;
    }

    public SequenceOntology getStructuralVariant() {
        return structuralVariant;
    }

    public void setStructuralVariant(SequenceOntology structuralVariant) {
        this.structuralVariant = structuralVariant;
    }
}
