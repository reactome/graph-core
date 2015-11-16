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
 * Event is a container class. Events generally describe the conversion of input to output entities
 */
@NodeEntity
public abstract class Event extends DatabaseObject {

    @Relationship(type = "IS_REGULATED", direction = Relationship.INCOMING)
    private Set<Regulation> regulation;

    public Event() {}

    public Event(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<Regulation> getRegulation() {
        return regulation;
    }

    public void setRegulation(Set<Regulation> regulation) {
        this.regulation = regulation;
    }
}
