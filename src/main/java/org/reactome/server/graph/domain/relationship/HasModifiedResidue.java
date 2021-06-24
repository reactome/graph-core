package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.AbstractModifiedResidue;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

/**
 * HasModifiedResidue is the relationship hasModifiedResidue of EntityWithAccessionedSequence.
 * It is needed to specify the stoichiometry and order of AbstractModifiedResidue instances.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasModifiedResidue extends AbstractRelationship {
    @Id
    @GeneratedValue
    protected Long id;
    @TargetNode
    private AbstractModifiedResidue abstractModifiedResidue;

    public HasModifiedResidue() {
    }

    public AbstractModifiedResidue getAbstractModifiedResidue() {
        return abstractModifiedResidue;
    }

    public void setAbstractModifiedResidue(AbstractModifiedResidue abstractModifiedResidue) {
        this.abstractModifiedResidue = abstractModifiedResidue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(abstractModifiedResidue, ((HasModifiedResidue) o).getAbstractModifiedResidue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(abstractModifiedResidue);
    }
}
