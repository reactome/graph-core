package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * RepeatedUnit is the relationship entity of Polymer. It is needed to specify the stoichiometry (stoichiometry) of
 * repeatedUnits.
 */
@RelationshipProperties
public class RepeatedUnit extends Has<PhysicalEntity> {
    @Override
    public String getType() {
        return "repeatedUnit";
    }

    @JsonIgnore
    public PhysicalEntity getPhysicalEntity() {
        return element;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.element = physicalEntity;
    }

}
