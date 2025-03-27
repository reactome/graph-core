package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.HasMember;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Stream;

/**
 * Two or more entities grouped because of a shared molecular feature. The superclass for CandidateSet and DefinedSet.
 */
@SuppressWarnings("unused")
@Node
public abstract class EntitySet extends PhysicalEntity implements CompositionAggregator {

    @ReactomeProperty
    private Boolean isOrdered;

    @Relationship(type = Relationships.HAS_MEMBER)
    private SortedSet<HasMember> hasMember;

    @Relationship(type = Relationships.SPECIES)
    private List<Species> species;

    @Relationship(type = Relationships.RELATED_SPECIES)
    private List<Species> relatedSpecies;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(hasMember);
    }

    public EntitySet() {
    }

    public Boolean getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public SortedSet<HasMember> getMembers() {
        return hasMember;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setMembers(SortedSet<HasMember> hasMember) {
        this.hasMember = hasMember;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getHasMember() {
        return Has.Util.expandStoichiometry(hasMember);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setHasMember(List<PhysicalEntity> hasMember) {
        this.hasMember = Has.Util.aggregateStoichiometry(hasMember, HasMember::new);
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
