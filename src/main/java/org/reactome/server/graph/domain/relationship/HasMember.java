package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * HasMember is the relationship entity of EntitySets. It is needed to specify the order of members.
 */
@RelationshipProperties
public class HasMember implements Comparable<HasMember> {
    @Id @GeneratedValue private Long id;
    @TargetNode private PhysicalEntity physicalEntity;

    private Integer stoichiometry = 1;
    private int order;

    public HasMember() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(physicalEntity, ((HasMember) o).physicalEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(physicalEntity);
    }

    @Override
    public int compareTo(HasMember o) {
        return this.order - o.order;
    }
}
