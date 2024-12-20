package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.RepeatedUnit;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Stream;

/**
 * Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown. The repeated unit(s) is(are) identified in the repeatedUnit slot.
 * <p>
 * Logic in getter/setter of input and output is needed for retrieving data import using the GKInstance.
 * This is still used for testing if graph and sql produce the same data import
 */
@SuppressWarnings("unused")
@Node
public class Polymer extends PhysicalEntity implements CompositionAggregator {

    @ReactomeProperty
    private Integer maxUnitCount;
    @ReactomeProperty
    private Integer minUnitCount;

    @Relationship(type = "repeatedUnit")
    private SortedSet<RepeatedUnit> repeatedUnit;

    @Relationship(type = "species")
    private List<Species> species;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(repeatedUnit);
    }

    public Polymer() {
    }

    public Polymer(Long dbId) {
        super(dbId);
    }

    public Integer getMaxUnitCount() {
        return maxUnitCount;
    }

    public void setMaxUnitCount(Integer maxUnitCount) {
        this.maxUnitCount = maxUnitCount;
    }

    public Integer getMinUnitCount() {
        return minUnitCount;
    }

    public void setMinUnitCount(Integer minUnitCount) {
        this.minUnitCount = minUnitCount;
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchRepeatedUnit() {
        return Has.Util.simplifiedSort(repeatedUnit);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getRepeatedUnit() {
        return Has.Util.expandStoichiometry(repeatedUnit);
    }

    @JsonView(StoichiometryView.Nested.class)
    public SortedSet<RepeatedUnit> getRepeatedUnits() {
        return repeatedUnit;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setRepeatedUnit(List<PhysicalEntity> repeatedUnit) {
        this.repeatedUnit = Has.Util.aggregateStoichiometry(repeatedUnit, RepeatedUnit::new);
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setRepeatedUnits(SortedSet<RepeatedUnit> repeatedUnits) {
        this.repeatedUnit = repeatedUnits;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown. " +
                "The repeated unit(s) is(are) identified in the repeatedUnit slot";
    }
}
