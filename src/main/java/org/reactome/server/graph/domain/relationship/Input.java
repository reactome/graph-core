package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.PhysicalEntity;

/**
 * Input is the relationship entity of ReactionLikeEvent. It is needed to specify the stoichiometry (stoichiometry) of
 * inputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "input")
public class Input implements Comparable {

    @JsonIgnore
    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Input input = (Input) o;

        if (event != null ? !event.equals(input.event) : input.event != null) return false;
        return physicalEntity != null ? physicalEntity.equals(input.physicalEntity) : input.physicalEntity == null;
    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
