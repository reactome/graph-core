package org.reactome.server.tools.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.model.Polymer;

/**
 * Output is the relationship entity of ReactionLikeEvent. It is needed to specify the stoichiometry (stoichiometry) of
 * outputs.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "repeatedUnit")
public class RepeatedUnit {

    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    @StartNode
    private Polymer polymer;
    @EndNode
    private PhysicalEntity physicalEntity;

    public RepeatedUnit() {}

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public Polymer getPolymer() {
        return polymer;
    }

    public void setPolymer(Polymer polymer) {
        this.polymer = polymer;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }
}
