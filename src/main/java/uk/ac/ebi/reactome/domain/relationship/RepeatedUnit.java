package uk.ac.ebi.reactome.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import uk.ac.ebi.reactome.domain.model.PhysicalEntity;
import uk.ac.ebi.reactome.domain.model.Polymer;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 *
 * Output is the relationship entity of ReactionLikeEvent. It is needed to specify the cardinality (stoichiometry) of
 * outputs.
 */
@RelationshipEntity(type = "HAS_REPEATED_UNIT")
public class RepeatedUnit {

    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Integer cardinality;

    @StartNode
    private Polymer polymer;
    @EndNode
    private PhysicalEntity physicalEntity;

    public RepeatedUnit() {}

    public Integer getCardinality() {
        return cardinality;
    }

    @SuppressWarnings("unused")
    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    public Polymer getPolymer() {
        return polymer;
    }

    @SuppressWarnings("unused")
    public void setPolymer(Polymer polymer) {
        this.polymer = polymer;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    @SuppressWarnings("unused")
    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
