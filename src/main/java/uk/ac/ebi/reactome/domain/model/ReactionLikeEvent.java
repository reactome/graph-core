package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.HasInput;
import uk.ac.ebi.reactome.domain.relationship.HasOutput;

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

    @Relationship(type = "HAS_INPUT", direction = Relationship.OUTGOING)
    private Set<HasInput> input;

    @Relationship(type = "HAS_OUTPUT", direction = Relationship.OUTGOING)
    private Set<HasOutput>output;

//    @Relationship(type = "INPUT", direction = Relationship.INCOMING)
//    private Set<PhysicalEntity> input;
//
//    @Relationship(type = "OUTPUT", direction = Relationship.OUTGOING)
//    private Set<PhysicalEntity> output;

    public ReactionLikeEvent() {}

    public ReactionLikeEvent(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<HasOutput> getOutput() {
        return output;
    }

    @SuppressWarnings("unused")
    public void setOutput(Set<HasOutput> output) {
        this.output = output;
    }

    public Set<HasInput> getInput() {
        return input;
    }

    @SuppressWarnings("unused")
    public void setInput(Set<HasInput> input) {
        this.input = input;
    }

//
//    public Set<PhysicalEntity> getInput() {
//        return input;
//    }
//
//    public void setInput(Set<PhysicalEntity> input) {
//        this.input = input;
//    }
//
//    public Set<PhysicalEntity> getOutput() {
//        return output;
//    }
//
//    public void setOutput(Set<PhysicalEntity> output) {
//        this.output = output;
//    }
}
