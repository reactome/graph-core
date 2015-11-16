package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 * Input is the relationship entity of ReactionLikeEvent. It is needed to specify the cardinality (stoichiometry) of
 * inputs.
 */
@RelationshipEntity(type = "INPUT")
public class Input {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality;

    @StartNode
    private PhysicalEntity physicalEntity;
    @EndNode
    private Event event;

    public Input() {}

    public Integer getCardinality() {
        return cardinality;
    }

    @SuppressWarnings("unused")
    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @SuppressWarnings("unused")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

    public Event getEvent() {
        return event;
    }

    @SuppressWarnings("unused")
    public void setEvent(Event event) {
        this.event = event;
    }
}
