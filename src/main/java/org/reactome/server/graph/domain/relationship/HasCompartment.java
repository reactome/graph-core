package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Compartment;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasCompartment is the relationship compartment of Event and PhysicalEntity.
 *
 * It is basically needed to specify the order of compartments.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasCompartment extends Has<Compartment> {

    @Override
    public String getType() {
        return "compartment";
    }

    @JsonIgnore
    public Compartment getCompartment() {
        return element;
    }

    public void setCompartment(Compartment compartment) {
        this.element = compartment;
    }
}
