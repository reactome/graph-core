package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Cell extends PhysicalEntity {
    @Relationship(type = "rnaMarker")
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

    public Cell() {

    }

    public List<EntityWithAccessionedSequence> getRNAMarker() {
        return RNAMarker;
    }

    @Relationship(type = "rnaMarker")
    public void setRNAMarker(List<EntityWithAccessionedSequence> rNAMarker) {
        RNAMarker = rNAMarker;
    }

    public List<MarkerReference> getMarkerReference() {
        return markerReference;
    }

    @Relationship(type = "markerReference")
    public void setMarkerReference(List<MarkerReference> markerReference) {
        this.markerReference = markerReference;
    }

    public Anatomy getOrgan() {
        return organ;
    }

    @Relationship(type = "organ")
    public void setOrgan(Anatomy organ) {
        this.organ = organ;
    }

    public List<EntityWithAccessionedSequence> getProteinMarker() {
        return proteinMarker;
    }

    @Relationship(type = "proteinMarker")
    public void setProteinMarker(List<EntityWithAccessionedSequence> proteinMarker) {
        this.proteinMarker = proteinMarker;
    }

    public List<Taxon> getSpecies() {
        return species;
    }

    @Relationship(type = "species")
    public void setSpecies(List<Taxon> species) {
        this.species = species;
    }

    public Anatomy getTissue() {
        return tissue;
    }

    @Relationship(type = "tissue")
    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

    public Anatomy getTissueLayer() {
        return tissueLayer;
    }

    @Relationship(type = "tissueLayer")
    public void setTissueLayer(Anatomy tissueLayer) {
        this.tissueLayer = tissueLayer;
    }
}
