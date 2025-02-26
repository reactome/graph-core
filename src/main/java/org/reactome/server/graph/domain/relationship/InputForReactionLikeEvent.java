package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * ConsumedBy -> bi-directionality for
 */
@RelationshipProperties
public class InputForReactionLikeEvent extends Has<ReactionLikeEvent> {

    @Override
    public String getType() {
        return "inputOf";
    }

    @JsonIgnore
    public ReactionLikeEvent getReactionLikeEvent() {
        return element;
    }

    public void setReactionLikeEvent(ReactionLikeEvent event) {
        this.element = event;
    }

}
