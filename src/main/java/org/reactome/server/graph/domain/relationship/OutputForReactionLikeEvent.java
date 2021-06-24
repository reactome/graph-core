package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * Output relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of outputs.
 */
@RelationshipProperties
public class OutputForReactionLikeEvent extends AbstractOutput {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode
    private ReactionLikeEvent reactionLikeEvent;

    public OutputForReactionLikeEvent() {
    }

    public ReactionLikeEvent getReactionLikeEvent() {
        return reactionLikeEvent;
    }

    public void setReactionLikeEvent(ReactionLikeEvent reactionLikeEvent) {
        this.reactionLikeEvent = reactionLikeEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(reactionLikeEvent, ((OutputForReactionLikeEvent) o).reactionLikeEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reactionLikeEvent);
    }
}
