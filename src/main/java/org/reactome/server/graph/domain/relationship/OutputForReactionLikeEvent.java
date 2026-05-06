package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * Output relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of outputs.
 */
@RelationshipProperties
public class OutputForReactionLikeEvent extends Has<ReactionLikeEvent> {
    @Override
    public String getType() {
        return "outputOf";
    }

    @JsonIgnore
    public ReactionLikeEvent getReactionLikeEvent() {
        return element;
    }

    public void setReactionLikeEvent(ReactionLikeEvent reactionLikeEvent) {
        this.element = reactionLikeEvent;
    }
}
