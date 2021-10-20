package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class NegativePrecedingEvent extends DatabaseObject {

    @ReactomeProperty
    private String comment;

    @Relationship(type = "negativePrecedingEvent", direction = Relationship.Direction.INCOMING)
    private List<Event> precedingEvent;

    @Relationship(type = "reason")
    private NegativePrecedingEventReason reason;

    public NegativePrecedingEvent() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    public NegativePrecedingEventReason getReason() {
        return reason;
    }

    public void setReason(NegativePrecedingEventReason reason) {
        this.reason = reason;
    }

}