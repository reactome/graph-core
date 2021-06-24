package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * HasComponent is the relationship entity of Complexes. It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasComponent extends AbstractHasComponent {
    @Id
    @GeneratedValue
    protected Long id;

    @TargetNode
    private PhysicalEntity physicalEntity;

    public HasComponent() {
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
        return Objects.equals(physicalEntity, ((HasComponent) o).physicalEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalEntity);
    }
}
