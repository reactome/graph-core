package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

import java.util.List;

/**
 * Two or more entities grouped because of a shared molecular feature. The superclass for CandidateSet, DefinedSet, and OpenSet.
 */
@SuppressWarnings("unused")
@NodeEntity
public class EntitySet extends PhysicalEntity{

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
}
