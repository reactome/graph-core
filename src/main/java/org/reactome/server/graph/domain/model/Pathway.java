package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.HasEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A collection of related Events. These events can be ReactionLikeEvents or Pathways
 */
@SuppressWarnings("unused")
@NodeEntity
public class Pathway extends Event {

    @ReactomeProperty
    private String doi;
    @ReactomeProperty(addedField = true)
    private Boolean hasDiagram;
    @ReactomeProperty
    private String isCanonical;

    @Relationship(type = "hasEvent")
    private SortedSet<HasEvent> hasEvent;

    @Relationship(type = "normalPathway")
    private Pathway normalPathway;

    public Pathway() {}

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Boolean getHasDiagram() {
        return hasDiagram;
    }

    public void setHasDiagram(Boolean hasDiagram) {
        this.hasDiagram = hasDiagram;
    }

    public String getIsCanonical() {
        return isCanonical;
    }

    public void setIsCanonical(String isCanonical) {
        this.isCanonical = isCanonical;
    }

    @Relationship(type = "hasEvent")
    public List<Event> getHasEvent() {
        if (hasEvent == null) return null;
        List<Event> rtn = new ArrayList<>();
        for (HasEvent he : hasEvent) {
            rtn.add(he.getEvent());
        }
        return rtn;
    }

    @Relationship(type = "hasEvent")
    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = new TreeSet<>();
        int order = 0;
        for (Event event : hasEvent) {
            HasEvent aux = new HasEvent();
            aux.setPathway(this);
            aux.setEvent(event);
            aux.setOrder(order++);
            this.hasEvent.add(aux);
        }
    }

    public Pathway getNormalPathway() {
        return normalPathway;
    }

    @Relationship(type = "normalPathway")
    public void setNormalPathway(Pathway normalPathway) {
        this.normalPathway = normalPathway;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A collection of related Events. These events can be ReactionLikeEvents or Pathways";
    }
}
