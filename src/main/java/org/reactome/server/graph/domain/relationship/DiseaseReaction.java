package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * Output relationship of ReactionLikeEvent. It is needed to specify the stoichiometry and order of outputs.
 */
@RelationshipProperties
public class DiseaseReaction extends Has<ReactionLikeEvent> {
    @Override
    public String getType() {
        return "diseaseReaction";
    }

    @JsonIgnore
    public ReactionLikeEvent getDiseaseReaction() {
        return element;
    }

    public void setDiseaseReaction(ReactionLikeEvent reactionLikeEvent) {
        this.element = reactionLikeEvent;
    }
}
