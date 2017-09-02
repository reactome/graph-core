package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

import java.util.List;

/**
 * Two or more entities grouped because of a shared molecular feature. The superclass for CandidateSet, DefinedSet, and OpenSet.
 */
@SuppressWarnings("unused")
@NodeEntity
public abstract class EntitySet extends PhysicalEntity{

    @ReactomeProperty
    private Boolean isOrdered;

    @Relationship(type = "hasMember")
    private List<PhysicalEntity> hasMember;

    @Relationship(type = "species")
    private List<Species> species;

    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;

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

    @Relationship(type = "hasMember")
    public void setHasMember(List<PhysicalEntity> hasMember) {
        this.hasMember = hasMember;
    }

    public List<Species> getSpecies() {
        return species;
    }

    @Relationship(type = "species")
    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    @Relationship(type = "relatedSpecies")
    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Two or more entities grouped because of a shared molecular feature. " +
                "The superclass for CandidateSet, DefinedSet, and OpenSet";
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Set";
    }
}
