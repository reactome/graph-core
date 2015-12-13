package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * BlackBoxEvent contains "unbalanced" reactions like synthesis or degradation and "shortcut" reactions representing
 * more complex processes
 */
@NodeEntity
public class BlackBoxEvent extends ReactionLikeEvent {

    @Relationship(type = "templateEvent")
    private Event templateEvent;

    @Relationship(type = "hasEvent")
    private List<Event> hasEvent;

    public BlackBoxEvent() {}

    public Event getTemplateEvent() {
        return this.templateEvent;
    }

    public void setTemplateEvent(Event templateEvent) {
        this.templateEvent = templateEvent;
    }

    public List<Event> getHasEvent() {
        return hasEvent;
    }

    public void setHasEvent(List<Event> hasEvent) {
        this.hasEvent = hasEvent;
    }

}
