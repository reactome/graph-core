package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;

/**
 * HasComponent is the relationship entity of Complexes. It is needed to specify the stoichiometry (stoichiometry) of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "hasComponent")
public class HasComponent {

    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    @StartNode
    private Complex complex;
    @EndNode
    private PhysicalEntity physicalEntity;

    public HasComponent() {}

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HasComponent that = (HasComponent) o;

        if (complex != null ? !complex.equals(that.complex) : that.complex != null) return false;
        return physicalEntity != null ? physicalEntity.equals(that.physicalEntity) : that.physicalEntity == null;
    }

    @Override
    public int hashCode() {
        int result = complex != null ? complex.hashCode() : 0;
        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
        return result;
    }
}
