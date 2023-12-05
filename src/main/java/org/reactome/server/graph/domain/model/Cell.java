package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.annotations.ReactomeRelationship;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class Cell extends PhysicalEntity {
    @Relationship(type = "rnaMarker")
    @ReactomeRelationship(originName = "RNAMarker")
    private List<EntityWithAccessionedSequence> rnaMarker;

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

    public Cell() {

    }

    @JsonGetter("rnaMarker")
    public List<EntityWithAccessionedSequence> getRnaMarker() {
        return rnaMarker;
    }

    public void setRnaMarker(List<EntityWithAccessionedSequence> rnaMarker) {
        this.rnaMarker = rnaMarker;
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
