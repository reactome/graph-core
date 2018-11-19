package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@RelationshipEntity(type = "hasEncapsulatedEvent")
@SuppressWarnings("unused")
public class HasEncapsulatedEvent implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private Pathway pathway;

    @EndNode
    private Event event;

    private int order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pathway getPathway() {
        return pathway;
    }

    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
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
    public int compareTo(Object o) {
        return this.order - ((HasEncapsulatedEvent) o).order;
    }
}
