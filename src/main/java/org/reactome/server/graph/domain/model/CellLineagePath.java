package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.relationship.HasEvent;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class CellLineagePath extends Event {

    @Relationship(type = "hasEvent")
    private List<HasEvent> hasEvent;

    @Relationship(type = "tissue")
    private Anatomy tissue;

    public CellLineagePath() {
    }

    public Anatomy getTissue() {
        return tissue;
    }

    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

    public List<HasEvent> getHasEvent() {
        return hasEvent;
    }

    public void setHasEvent(List<HasEvent> hasEvent) {
        this.hasEvent = hasEvent;
    }
}