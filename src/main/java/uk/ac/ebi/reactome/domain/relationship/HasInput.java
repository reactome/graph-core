package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;
import uk.ac.ebi.reactome.domain.model.ReactionLikeEvent;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 * HasInput is the relationship entity of ReactionLikeEvent. It is needed to specify the cardinality (stoichiometry) of
 * inputs.
 */
@RelationshipEntity(type = "HAS_INPUT")
public class HasInput {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality;

    @StartNode
    private ReactionLikeEvent reactionLikeEvent;

    @EndNode
    private PhysicalEntity physicalEntity;

    public HasInput() {}

    public Integer getCardinality() {
        return cardinality;
    }

    @SuppressWarnings("unused")
    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    public ReactionLikeEvent getReactionLikeEvent() {
        return reactionLikeEvent;
    }

    public void setReactionLikeEvent(ReactionLikeEvent reactionLikeEvent) {
        this.reactionLikeEvent = reactionLikeEvent;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
