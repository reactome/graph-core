package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * Output relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of outputs.
 */
@RelationshipProperties
public class Output extends Has<PhysicalEntity> {
    @Override
    public String getType() {
        return "output";
    }

    @JsonIgnore
    public PhysicalEntity getPhysicalEntity() {
        return element;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.element = physicalEntity;
    }
}
