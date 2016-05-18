package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

/**
 * Two or more entities grouped because of a shared molecular feature. The superclass for CandidateSet, DefinedSet, and OpenSet.
 */
@SuppressWarnings("unused")
@NodeEntity
public abstract class EntitySet extends PhysicalEntity{

    @ReactomeProperty
    private Boolean isOrdered;

    @Relationship(type = "hasMember", direction = Relationship.OUTGOING)
    private List<PhysicalEntity> hasMember;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    private List<Species> species;

    public EntitySet() {}

    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public List<PhysicalEntity> getHasMember() {
        return hasMember;
    }

    public void setHasMember(List<PhysicalEntity> hasMember) {
        this.hasMember = hasMember;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    @Override
    public String getExplanation() {
        return "Two or more entities grouped because of a shared molecular feature. " +
                "The superclass for CandidateSet, DefinedSet, and OpenSet";
    }

    @Override
    public String getClassName() {
        return "Set";
    }
}
