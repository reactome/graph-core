package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@NodeEntity
public class NegativePrecedingEvent extends DatabaseObject {

    @ReactomeProperty
    private String comment;

    @Relationship(type = "negativePrecedingEvent", direction = Relationship.INCOMING)
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

    @Relationship(type = "negativePrecedingEvent", direction = Relationship.INCOMING)
    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    @Relationship(type = "negativePrecedingEvent", direction = Relationship.INCOMING)
    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    @Relationship(type = "reason")
    public NegativePrecedingEventReason getReason() {
        return reason;
    }

    @Relationship(type = "reason")
    public void setReason(NegativePrecedingEventReason reason) {
        this.reason = reason;
    }

}