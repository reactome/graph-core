package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class CellLineagePath extends Event {

    @Relationship(type = "hasEvent")
    private List<Event> hasEvent;

    @Relationship(type = "tissue")
    private Anatomy tissue;

    public CellLineagePath() { }

    public Anatomy getTissue() {
        return tissue;
    }

    @Relationship(type = "tissue")
    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

    public List<Event> getHasEvent() {
        return hasEvent;
    }

    @Relationship(type = "hasEvent")
    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = hasEvent;
    }
}