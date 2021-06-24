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
public class RepeatedUnitForPhysicalEntity extends AbstractRepeatedUnit {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode
    private Polymer polymer;

    public RepeatedUnitForPhysicalEntity() {
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

}
