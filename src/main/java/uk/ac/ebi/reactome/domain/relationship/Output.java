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
 * Output is the relationship entity of ReactionLikeEvent. It is needed to specify the cardinality (stoichiometry) of
 * outputs.
 */
@RelationshipEntity(type = "output")
public class Output {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality = 1;

    @StartNode
    private Event event;
    @EndNode
    private PhysicalEntity physicalEntity;

    public Output() {}

    public Integer getCardinality() {
        return cardinality;
    }

    @SuppressWarnings("unused")
    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    public Event getEvent() {
        return event;
    }

    @SuppressWarnings("unused")
    public void setEvent(Event event) {
        this.event = event;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @SuppressWarnings("unused")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
