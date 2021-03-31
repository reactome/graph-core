package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * Input relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of inputs.
 */
@RelationshipProperties
public class Input implements Comparable<Input> {

    @Id @GeneratedValue
    private Long id;

    @TargetNode
    private PhysicalEntity target;

    private ReactionLikeEvent rle;

    private Integer stoichiometry = 1;

    private int order;

    public Input() {}

    public PhysicalEntity getPhysicalEntity() {
        return target;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.target = physicalEntity;
    }

    public ReactionLikeEvent getReactionLikeEvent() {
        return rle;
    }

    public void setReactionLikeEvent(ReactionLikeEvent event) {
        this.rle = event;
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
        return Objects.equals(target, ((Input) o).target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }

    @Override
    public int compareTo(Input o) {
        return this.order - o.order;
    }
}
