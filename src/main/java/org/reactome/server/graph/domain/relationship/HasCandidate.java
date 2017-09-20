package org.reactome.server.graph.domain.relationship;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.EntitySet;
import org.reactome.server.graph.domain.model.PhysicalEntity;

/**
 * HasCandidate is the relationship entity of CandidateSets. It is needed to specify the order of members.
 */
@SuppressWarnings("unused")
@RelationshipEntity(type = "hasCandidate")
public class HasCandidate implements Comparable {

    @GraphId
    private Long id;

    @StartNode
    private EntitySet entitySet;

    @EndNode
    private PhysicalEntity physicalEntity;

    private Integer stoichiometry = 1;

    private int order;

    public HasCandidate() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(EntitySet entitySet) {
        this.entitySet = entitySet;
    }

    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
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

        HasCandidate that = (HasCandidate) o;

        if (entitySet != null ? !entitySet.equals(that.entitySet) : that.entitySet != null) return false;
        return physicalEntity != null ? physicalEntity.equals(that.physicalEntity) : that.physicalEntity == null;
    }

    @Override
    public int hashCode() {
        int result = entitySet != null ? entitySet.hashCode() : 0;
        result = 31 * result + (physicalEntity != null ? physicalEntity.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((HasCandidate) o).order;
    }
}
