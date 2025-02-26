package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class HasEncapsulatedEvent extends Has<Event> {
    @Override
    public String getType() {
        return "encapsulatedEvent";
    }

    @JsonIgnore
    public Event getEvent() {
        return element;
    }

    public void setEvent(Event event) {
        this.element = event;
    }

}
