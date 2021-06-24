package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Complex;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * HasComponentForComplex is the incoming relationship for HasComponent (SDN6) is the relationship entity of Complexes.
 * It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasComponentForComplex extends AbstractHasComponent {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode
    private Complex complex;

    public HasComponentForComplex() {
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(complex, ((HasComponentForComplex) o).complex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complex);
    }

}
