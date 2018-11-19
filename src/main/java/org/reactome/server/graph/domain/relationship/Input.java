package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;

/**
 * Input relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of inputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "input")
public class Input implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private ReactionLikeEvent rle;

    @EndNode
    private PhysicalEntity target;

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

        Input that = (Input) o;

        if (rle != null ? !rle.equals(that.rle) : that.rle != null) return false;
        return target != null ? target.equals(that.target) : that.target == null;
    }

    @Override
    public int hashCode() {
        int result = rle != null ? rle.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((Input) o).order;
    }
}
