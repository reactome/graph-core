package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * A peptide or polynucleotide whose sequence is unknown and thus cannot be linked to external sequence databases or used for orthology inference.
 */
@SuppressWarnings("unused")
@NodeEntity
public class GenomeEncodedEntity extends PhysicalEntity {


    @Relationship(type = "species")
    private Taxon species;

    public GenomeEncodedEntity() {}

    public Taxon getSpecies() {
        return species;
    }

    public void setSpecies(Taxon species) {
        this.species = species;
    }

    @ReactomeSchemaIgnore
    @Override
    public String getExplanation() {
        return "A peptide or polynucleotide whose sequence is unknown and thus cannot be linked to external sequence databases or used for orthology inference";
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Genes and Transcripts";
    }
}
