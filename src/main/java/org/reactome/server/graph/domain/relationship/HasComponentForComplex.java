package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Complex;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasComponentForComplex is the incoming relationship for HasComponent (SDN6) is the relationship entity of Complexes.
 * It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasComponentForComplex extends Has<Complex> {

    @Override
    public String getType() {
        return "componentOf";
    }

    @JsonIgnore
    public Complex getComplex() {
        return element;
    }

    public void setComplex(Complex complex) {
        this.element = complex;
    }
}
