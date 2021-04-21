package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.HasMember;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

/**
 * Two or more entities grouped because of a shared molecular feature. The superclass for CandidateSet and DefinedSet.
 */
@SuppressWarnings("unused")
@Node
public abstract class EntitySet extends PhysicalEntity {

    @ReactomeProperty
    private Boolean isOrdered;

    @Relationship(type = "hasMember")
    private SortedSet<HasMember> hasMember;

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
        List<PhysicalEntity> rtn = null;
        if (hasMember != null) {
            rtn = new ArrayList<>();
            //stoichiometry does NOT need to be taken into account here
            for (HasMember component : hasMember) {
                rtn.add(component.getPhysicalEntity());
            }
        }
        return rtn;
    }

    public void setHasMember(List<PhysicalEntity> hasMember) {
        if (hasMember == null) return;
        Map<Long, HasMember> components = new LinkedHashMap<>();
        int order = 0;
        for (PhysicalEntity physicalEntity : hasMember) {
            //stoichiometry does NOT need to be taken into account here
            HasMember aux = new HasMember();
//            aux.setEntitySet(this);
            aux.setPhysicalEntity(physicalEntity);
            aux.setOrder(order++);
            components.put(physicalEntity.getDbId(), aux);

        }
        this.hasMember = new TreeSet<>(components.values());
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Two or more entities grouped because of a shared molecular feature. " +
                "The superclass for CandidateSet and DefinedSet";
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Set";
    }
}
