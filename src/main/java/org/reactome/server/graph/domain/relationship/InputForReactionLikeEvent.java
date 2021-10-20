package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * ConsumedBy -> bi-directionality for
 */
@RelationshipProperties
public class InputForReactionLikeEvent implements Comparable<InputForReactionLikeEvent> {
    @Id @GeneratedValue private Long id;
    @TargetNode private ReactionLikeEvent reactionLikeEvent;

    private Integer stoichiometry = 1;
    private int order;

    public InputForReactionLikeEvent() {}

    public ReactionLikeEvent getReactionLikeEvent() {
        return reactionLikeEvent;
    }

    public void setReactionLikeEvent(ReactionLikeEvent event) {
        this.reactionLikeEvent = event;
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
        return Objects.equals(reactionLikeEvent, ((InputForReactionLikeEvent) o).reactionLikeEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reactionLikeEvent);
    }

    @Override
    public int compareTo(InputForReactionLikeEvent o) {
        return this.order - o.order;
    }
}
