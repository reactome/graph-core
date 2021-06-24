package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

@RelationshipProperties
public class HasEvent extends AbstractRelationship {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event hasEvent) {
        this.event = hasEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(event, ((HasEvent)o).event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }

    @Override
    public String toString() {
        return "HasEvent{" +
                "event=" + event +
                ", order=" + order +
                ", stoichiometry=" + stoichiometry +
                '}';
    }
}
