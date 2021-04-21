package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class FunctionalStatus extends DatabaseObject {

    @Relationship(type = "functionalStatusType")
    private FunctionalStatusType functionalStatusType;

    @Relationship(type = "structuralVariant")
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
