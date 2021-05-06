package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Compartment;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * HasCompartment is the relationship compartment of Event and PhysicalEntity.
 *
 * It is basically needed to specify the order of compartments.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasCompartment implements Comparable<HasCompartment> {
    @Id @GeneratedValue private Long id;
    @TargetNode private Compartment compartment;

    private int order;

    public HasCompartment() {}

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(compartment, ((HasCompartment) o).compartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compartment);
    }

    @Override
    public int compareTo(HasCompartment o) {
        return this.order - o.order;
    }
}
