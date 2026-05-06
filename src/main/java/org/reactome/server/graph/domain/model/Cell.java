package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.reactome.server.graph.domain.relationship.Has;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Node
public class Cell extends PhysicalEntity implements CompositionAggregator {
    @Relationship(type = "RNAMarker")
    private List<EntityWithAccessionedSequence> RNAMarker;

    @Relationship(type = "markerReference")
    private List<MarkerReference> markerReference;

    @Relationship(type = "organ")
    private Anatomy organ;

    @Relationship(type = "proteinMarker")
    private List<EntityWithAccessionedSequence> proteinMarker;

    @Relationship(type = "species")
    private List<Taxon> species;

    @Relationship(type = "tissue")
    private Anatomy tissue;

    @Relationship(type = "tissueLayer")
    private Anatomy tissueLayer;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.of(
                Has.Util.wrapUniqueElements(RNAMarker, "rnaMarker"),
                Has.Util.wrapUniqueElements(proteinMarker, "proteinMarker")
        );
    }

    public Cell() {

    }

    @JsonGetter("rnaMarker")
    public List<EntityWithAccessionedSequence> getRNAMarker() {
        return RNAMarker;
    }

    public void setRNAMarker(List<EntityWithAccessionedSequence> rNAMarker) {
        RNAMarker = rNAMarker;
    }

    public List<MarkerReference> getMarkerReference() {
        return markerReference;
    }

    public void setMarkerReference(List<MarkerReference> markerReference) {
        this.markerReference = markerReference;
    }

    public Anatomy getOrgan() {
        return organ;
    }

    public void setOrgan(Anatomy organ) {
        this.organ = organ;
    }

    public List<EntityWithAccessionedSequence> getProteinMarker() {
        return proteinMarker;
    }

    public void setProteinMarker(List<EntityWithAccessionedSequence> proteinMarker) {
        this.proteinMarker = proteinMarker;
    }

    public List<Taxon> getSpecies() {
        return species;
    }

    public void setSpecies(List<Taxon> species) {
        this.species = species;
    }

    public Anatomy getTissue() {
        return tissue;
    }

    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

    public Anatomy getTissueLayer() {
        return tissueLayer;
    }

    public void setTissueLayer(Anatomy tissueLayer) {
        this.tissueLayer = tissueLayer;
    }
}
