package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * Input relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of inputs.
 */
@RelationshipProperties
public class Input extends AbstractInput {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode
    private PhysicalEntity physicalEntity;

    public Input() {
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(physicalEntity, ((Input) o).physicalEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalEntity);
    }
}
