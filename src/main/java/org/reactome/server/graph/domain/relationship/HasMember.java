package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasMember is the relationship entity of EntitySets. It is needed to specify the order of members.
 */
@RelationshipProperties
public class HasMember extends Has<PhysicalEntity> {
    @Override
    public String getType() {
        return "member";
    }

    @JsonIgnore
    public PhysicalEntity getPhysicalEntity() {
        return element;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.element = physicalEntity;
    }
}
