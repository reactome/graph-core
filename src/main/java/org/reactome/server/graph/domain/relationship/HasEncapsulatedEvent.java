package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

@RelationshipProperties
public class HasEncapsulatedEvent implements Comparable<HasEncapsulatedEvent> {
    @Id @GeneratedValue private Long id;
    @TargetNode private Event event;

    private int order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event hasEvent) {
        this.event = hasEvent;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(event, ((HasEncapsulatedEvent) o).event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }

    @Override
    public int compareTo(HasEncapsulatedEvent o) {
        return this.order - o.order;
    }
}
