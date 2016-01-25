package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Pathway extends Event {

    private String doi;
    private String isCanonical;
//    private Boolean hasDiagram;

    @Relationship(type="hasEvent")
    private List<Event> hasEvent;

    @Relationship(type = "normalPathway")
    private Pathway normalPathway;

    public Pathway() {}

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getIsCanonical() {
        return isCanonical;
    }

    public void setIsCanonical(String isCanonical) {
        this.isCanonical = isCanonical;
    }

//    public Boolean getHasDiagram() {
//        return hasDiagram;
//    }
//
//    public void setHasDiagram(Boolean hasDiagram) {
//        this.hasDiagram = hasDiagram;
//    }

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
