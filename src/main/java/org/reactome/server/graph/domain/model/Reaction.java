package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * Defines a change of state for one or more molecular entities. Most reactions in Reactome involve either: a) the interaction of entities to form a complex, or b) the movement of entities between compartments, or c) the chemical conversion of entities as part of a metabolic process. Reactions have a molecular balance between input and output entities.
 */
@SuppressWarnings("unused")
@Node
public class Reaction extends ReactionLikeEvent {

    @Relationship(type = "reverseReaction")
    private Reaction reverseReaction;

    public Reaction() {}

    public Reaction(Long dbId) {
        super(dbId);
    }

    public Reaction getReverseReaction() {
        return reverseReaction;
    }

    public void setReverseReaction(Reaction reverseReaction) {
        this.reverseReaction = reverseReaction;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Defines a change of state for one or more molecular entities. " +
                "Most reactions in Reactome involve either a) the interaction of entities to form a complex, or b) the movement of entities between compartments, or c) the chemical conversion of entities as part of a metabolic process. Reactions have a molecular balance between input and output entities";
    }
}
