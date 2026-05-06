package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.AbstractModifiedResidue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * HasModifiedResidue is the relationship hasModifiedResidue of EntityWithAccessionedSequence.
 * It is needed to specify the stoichiometry and order of AbstractModifiedResidue instances.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class HasModifiedResidue extends Has<AbstractModifiedResidue> {

    @Override
    public String getType() {
        return "modifiedResidue";
    }

    @JsonIgnore
    public AbstractModifiedResidue getAbstractModifiedResidue() {
        return element;
    }

    public void setAbstractModifiedResidue(AbstractModifiedResidue abstractModifiedResidue) {
        this.element = abstractModifiedResidue;
    }
}
