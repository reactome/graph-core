package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;

/**
 * Input is the relationship entity of ReactionLikeEvent. It is needed to specify the stoichiometry (stoichiometry) of
 * inputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "input")
public class Input {

    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    @StartNode
    private Event event;
    @EndNode
    private PhysicalEntity physicalEntity;

    public Input() {}

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
