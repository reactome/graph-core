package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasComponent is the relationship entity of Complexes. It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasComponent extends Has<PhysicalEntity> {

    @Override
    public String getType() {
        return "component";
    }

    @JsonIgnore
    public PhysicalEntity getPhysicalEntity() {
        return element;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.element = physicalEntity;
    }
}
