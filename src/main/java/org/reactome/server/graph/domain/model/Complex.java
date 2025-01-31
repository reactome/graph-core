package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.*;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.HasCompartment;
import org.reactome.server.graph.domain.relationship.HasComponent;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Stream;

/**
 * An entity formed by the association of two or more component entities (these components can themselves be complexes). Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components. At least one component must be specified.
 */
@SuppressWarnings("unused")
@Node
public class Complex extends PhysicalEntity implements CompositionAggregator {

    @ReactomeProperty
    private Boolean isChimeric;

    @Relationship(type = "hasComponent")
    private SortedSet<HasComponent> hasComponent;

    @ReactomeProperty
    private Boolean stoichiometryKnown;

    @Relationship(type = "entityOnOtherCell")
    private List<PhysicalEntity> entityOnOtherCell;

    @Relationship(type = "includedLocation")
    private SortedSet<HasCompartment> includedLocation;

    @Relationship(type = "species")
    private List<Species> species;

    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(hasComponent);
    }

    public Complex() {
    }

    public Complex(Long dbId) {
        super(dbId);
    }

    public Boolean getIsChimeric() {
        return isChimeric;
    }

    public void setIsChimeric(Boolean isChimeric) {
        this.isChimeric = isChimeric;
    }

    @JsonIgnore
    public List<StoichiometryObject> fetchHasComponent() {
        return Has.Util.simplifiedSort(this.hasComponent);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public SortedSet<HasComponent> getComponents() {
        return this.hasComponent;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setHasComponentNested(Collection<HasComponent> hasComponent) {
        this.hasComponent = new TreeSet<>(hasComponent);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getHasComponent() {
        return Has.Util.expandStoichiometry(this.hasComponent);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setHasComponent(List<PhysicalEntity> hasComponent) {
        this.hasComponent = Has.Util.aggregateStoichiometry(hasComponent, HasComponent::new);
    }

    public Boolean getStoichiometryKnown() {
        return stoichiometryKnown;
    }

    public void setStoichiometryKnown(Boolean stoichiometryKnown) {
        this.stoichiometryKnown = stoichiometryKnown;
    }

    public List<PhysicalEntity> getEntityOnOtherCell() {
        return entityOnOtherCell;
    }

    public void setEntityOnOtherCell(List<PhysicalEntity> entityOnOtherCell) {
        this.entityOnOtherCell = entityOnOtherCell;
    }

    public List<Compartment> getIncludedLocation() {
        return Has.Util.expandStoichiometry(includedLocation);
    }

    public void setIncludedLocation(SortedSet<HasCompartment> includedLocation) {
        this.includedLocation = includedLocation;
    }

    public void setIncludedLocation(List<Compartment> includedLocation) {
        this.includedLocation = Has.Util.aggregateStoichiometry(includedLocation, HasCompartment::new);
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
        return "An entity formed by the association of two or more component entities (these components can themselves be complexes). " +
                "At least one component must be specified. Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components";
    }
}
