package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * Defines a change of state for one or more molecular entities. Most reactions in Reactome involve either: a) the interaction of entities to form a complex, or b) the movement of entities between compartments, or c) the chemical conversion of entities as part of a metabolic process. Reactions have a molecular balance between input and output entities.
 */
@SuppressWarnings("unused")
@NodeEntity
public class Reaction extends ReactionLikeEvent {

    @Relationship(type = "reverseReaction", direction = Relationship.UNDIRECTED)
    private Reaction reverseReaction;

    public Reaction() {}

    public Reaction getReverseReaction() {
        return reverseReaction;
    }

    public void setReverseReaction(Reaction reverseReaction) {
        this.reverseReaction = reverseReaction;
    }

    @ReactomeSchemaIgnore
    @Override
    public String getExplanation() {
        return "Defines a change of state for one or more molecular entities. " +
                "Most reactions in Reactome involve either a) the interaction of entities to form a complex, or b) the movement of entities between compartments, or c) the chemical conversion of entities as part of a metabolic process. Reactions have a molecular balance between input and output entities";
    }
}
