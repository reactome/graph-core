package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.Polymer;

/**
 * RepeatedUnit is the relationship entity of Polymer. It is needed to specify the stoichiometry (stoichiometry) of
 * repeatedUnits.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "repeatedUnit")
public class RepeatedUnit implements Comparable {

    @GraphId
    private Long id;

    private Integer stoichiometry = 1;

    private Integer order;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepeatedUnit that = (RepeatedUnit) o;

        if (polymer != null ? !polymer.equals(that.polymer) : that.polymer != null) return false;
        return physicalEntity != null ? physicalEntity.equals(that.physicalEntity) : that.physicalEntity == null;
    }

    @Override
    public int hashCode() {
        int result = polymer != null ? polymer.hashCode() : 0;
        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((RepeatedUnit) o).order;
    }
}
