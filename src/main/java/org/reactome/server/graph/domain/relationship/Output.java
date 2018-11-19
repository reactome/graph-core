package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;

/**
 * Output relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of outputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "output")
public class Output implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private ReactionLikeEvent rle;

    @EndNode
    private PhysicalEntity physicalEntity;

    private Integer stoichiometry = 1;

    private int order;

    public Output() {}

    public ReactionLikeEvent getReactionLikeEvent() {
        return rle;
    }

    public void setReactionLikeEvent(ReactionLikeEvent reactionLikeEvent) {
        this.rle = reactionLikeEvent;
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

        Output output = (Output) o;

        //noinspection SimplifiableIfStatement
        if (rle != null ? !rle.equals(output.rle) : output.rle != null) return false;
        return physicalEntity != null ? physicalEntity.equals(output.physicalEntity) : output.physicalEntity == null;
    }

    @Override
    public int hashCode() {
        int result = rle != null ? rle.hashCode() : 0;
        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
