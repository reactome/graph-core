package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Pathway extends Event {

    private String doi;
    private String isCanonical;
//    minimum is type ... i think with direction=Relationship.OUTGOING its better
//    lists are ok
    @Relationship(type="hasEvent")
    private List<Event> hasEvent;

    private Boolean hasDiagram;
    @Relationship
    private Pathway normalPathway;

    public Pathway() {
    }

    public Pathway getNormalPathway() {
        return normalPathway;
    }

    public void setNormalPathway(Pathway normalPathway) {
        this.normalPathway = normalPathway;
    }

    public List<Event> getHasEvent() {
        return hasEvent;
    }

    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = hasEvent;
    }

    public Boolean getHasDiagram() {
        return this.hasDiagram;
    }

    public void setHasDiagram(Boolean hasDiagram) {
        this.hasDiagram = hasDiagram;
    }


    public String getDoi() {
        return this.doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getIsCanonical() {
        return this.isCanonical;
    }

    public void setIsCanonical(String isCanonical) {
        this.isCanonical = isCanonical;
    }

}
