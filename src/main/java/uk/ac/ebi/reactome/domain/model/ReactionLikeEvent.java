package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.Input;
import uk.ac.ebi.reactome.domain.relationship.Output;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * ReactionLikeEvent is a container class. ReactionLikeEvent represents an Event that converts inputs into outputs
 */
@NodeEntity
public abstract class ReactionLikeEvent extends Event {

    @Relationship(type = "INPUT", direction = Relationship.INCOMING)
    private Set<Input> input;

    @Relationship(type = "OUTPUT", direction = Relationship.OUTGOING)
    private Set<Output>output;

    public ReactionLikeEvent() {}

    public ReactionLikeEvent(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<Output> getOutput() {
        return output;
    }

    @SuppressWarnings("unused")
    public void setOutput(Set<Output> output) {
        this.output = output;
    }

    public Set<Input> getInput() {
        return input;
    }

    @SuppressWarnings("unused")
    public void setInput(Set<Input> input) {
        this.input = input;
    }
}
