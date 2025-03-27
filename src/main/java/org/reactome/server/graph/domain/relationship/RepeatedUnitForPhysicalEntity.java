package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Polymer;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * RepeatedUnit is the relationship entity of Polymer. It is needed to specify the stoichiometry (stoichiometry) of
 * repeatedUnits.
 */
@RelationshipProperties
public class RepeatedUnitForPhysicalEntity extends Has<Polymer> {

    @Override
    public String getType() {
        return "repeatedUnitOf";
    }

    @JsonIgnore
    public Polymer getPolymer() {
        return element;
    }

    public void setPolymer(Polymer polymer) {
        this.element = polymer;
    }
}
