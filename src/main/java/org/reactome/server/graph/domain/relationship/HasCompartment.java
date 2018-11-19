package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.Compartment;
import org.reactome.server.graph.domain.model.DatabaseObject;

/**
 * HasCompartment is the relationship compartment of Event and PhysicalEntity.
 *
 * It is basically needed to specify the order of compartments.
 *
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "hasComponent")
public class HasCompartment<T extends DatabaseObject> implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private T source;

    @EndNode
    private Compartment compartment;

    private int order;

    public HasCompartment() {}

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

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

        HasCompartment that = (HasCompartment) o;

        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return compartment != null ? compartment.equals(that.compartment) : that.compartment == null;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (compartment != null ? compartment.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((HasCompartment) o).order;
    }
}
