package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class Reaction extends ReactionLikeEvent {

    @Relationship(type = "reverseReaction", direction = Relationship.OUTGOING)
    private Reaction reverseReaction;

    public Reaction() {}

    public Reaction getReverseReaction() {
        return reverseReaction;
    }

    public void setReverseReaction(Reaction reverseReaction) {
        this.reverseReaction = reverseReaction;
    }

}
