package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasCandidate is the relationship entity of CandidateSets. It is needed to specify the order of members.
 */
@RelationshipProperties
public class HasCandidate extends Has<PhysicalEntity> {
    @Override
    public String getType() {
        return "candidate";
    }

    @JsonIgnore
    public PhysicalEntity getPhysicalEntity() {
        return element;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.element = physicalEntity;
    }
}
