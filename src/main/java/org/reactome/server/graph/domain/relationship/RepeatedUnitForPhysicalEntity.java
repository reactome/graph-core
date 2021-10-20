package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Polymer;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * RepeatedUnit is the relationship entity of Polymer. It is needed to specify the stoichiometry (stoichiometry) of
 * repeatedUnits.
 */
@RelationshipProperties
public class RepeatedUnitForPhysicalEntity implements Comparable<RepeatedUnitForPhysicalEntity> {
    @Id @GeneratedValue private Long id;
    @TargetNode private Polymer polymer;

    private Integer stoichiometry = 1;

    private Integer order;

    public RepeatedUnitForPhysicalEntity() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(polymer, ((RepeatedUnitForPhysicalEntity) o).polymer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polymer);
    }

    @Override
    public int compareTo(RepeatedUnitForPhysicalEntity o) {
        return this.order - o.order;
    }
}
