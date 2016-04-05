package org.reactome.server.tools.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.tools.domain.model.Event;
import org.reactome.server.tools.domain.model.PhysicalEntity;

/**
 * Output is the relationship entity of ReactionLikeEvent. It is needed to specify the stoichiometry (stoichiometry) of
 * outputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "output")
public class Output {

    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    @StartNode
    private Event event;
    @EndNode
    private PhysicalEntity physicalEntity;

    public Output() {}

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
