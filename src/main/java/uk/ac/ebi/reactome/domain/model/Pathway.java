package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

import java.util.List;

/**
 * A collection of related Events. These events can be ReactionLikeEvents or Pathways
 */
@SuppressWarnings("unused")
@NodeEntity
public class Pathway extends Event {

    @ReactomeProperty
    private String doi;
    @ReactomeProperty
    private Boolean hasDiagram;
    @ReactomeProperty
    private String isCanonical;

    @Relationship(type="hasEvent", direction = Relationship.OUTGOING)
    private List<Event> hasEvent;

    @Relationship(type = "normalPathway", direction = Relationship.OUTGOING)
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

    public List<Event> getHasEvent() {
        return hasEvent;
    }

    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = hasEvent;
    }

    public Pathway getNormalPathway() {
        return normalPathway;
    }

    public void setNormalPathway(Pathway normalPathway) {
        this.normalPathway = normalPathway;
    }
}
