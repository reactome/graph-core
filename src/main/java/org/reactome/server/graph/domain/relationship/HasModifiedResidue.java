package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.AbstractModifiedResidue;
import org.reactome.server.graph.domain.model.EntityWithAccessionedSequence;

/**
 * HasModifiedResidue is the relationship hasModifiedResidue of EntityWithAccessionedSequence.
 * It is needed to specify the stoichiometry and order of AbstractModifiedResidue instances.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "hasModifiedResidue")
public class HasModifiedResidue implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private EntityWithAccessionedSequence ewas;

    @EndNode
    private AbstractModifiedResidue abstractModifiedResidue;

    private Integer stoichiometry = 1;

    private int order;

    public HasModifiedResidue() {}

    public EntityWithAccessionedSequence getEntityWithAccessionedSequence() {
        return ewas;
    }

    public void setEntityWithAccessionedSequence(EntityWithAccessionedSequence ewas) {
        this.ewas = ewas;
    }

    public AbstractModifiedResidue getAbstractModifiedResidue() {
        return abstractModifiedResidue;
    }

    public void setAbstractModifiedResidue(AbstractModifiedResidue abstractModifiedResidue) {
        this.abstractModifiedResidue = abstractModifiedResidue;
    }

    public Integer getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HasModifiedResidue that = (HasModifiedResidue) o;

        if (ewas != null ? !ewas.equals(that.ewas) : that.ewas != null) return false;
        return abstractModifiedResidue != null ? abstractModifiedResidue.equals(that.abstractModifiedResidue) : that.abstractModifiedResidue == null;
    }

    @Override
    public int hashCode() {
        int result = ewas != null ? ewas.hashCode() : 0;
        result = 31 * result + (abstractModifiedResidue != null ? abstractModifiedResidue.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((HasModifiedResidue) o).order;
    }
}
