package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class HasEvent extends Has<Event> {

    @Override
    public String getType() {
        return "event";
    }

    @JsonIgnore
    public Event getEvent() {
        return element;
    }

    public void setEvent(Event hasEvent) {
        this.element = hasEvent;
    }
}
