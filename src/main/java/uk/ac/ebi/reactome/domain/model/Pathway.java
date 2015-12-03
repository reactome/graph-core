package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Pathway represent any grouping of Events
 */
@Configurable
@NodeEntity
public class Pathway extends Event {

    @Relationship(type = "HAS_EVENT", direction = Relationship.OUTGOING)
    private Set<Event>events;

    public Pathway() {}

    public Pathway(Long dbId, String stId, String name) {
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
