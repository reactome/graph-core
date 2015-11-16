package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * BlackBoxEvent contains "unbalanced" reactions like synthesis or degradation and "shortcut" reactions representing
 * more complex processes
 */
@NodeEntity
public class BlackBoxEvent extends ReactionLikeEvent{

    @Relationship(type = "HAS_EVENT", direction = Relationship.OUTGOING)
    private Set<Event> events;

    public BlackBoxEvent() {}

    public BlackBoxEvent(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<Event> getEvents() {
        return events;
    }

    @SuppressWarnings("unused")
    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
