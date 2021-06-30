package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * A peptide or polynucleotide whose sequence is unknown and thus cannot be linked to external sequence databases or used for orthology inference.
 */
@SuppressWarnings("unused")
@Node
public class GenomeEncodedEntity extends PhysicalEntity {


    @Relationship(type = "species")
    private Taxon species;

    public GenomeEncodedEntity() {}

    public GenomeEncodedEntity(Long dbId) {
        super(dbId);
    }

    public Taxon getSpecies() {
        return species;
    }

    public void setSpecies(Taxon species) {
        this.species = species;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A peptide or polynucleotide whose sequence is unknown and thus cannot be linked to external sequence databases or used for orthology inference";
    }

    @ReactomeSchemaIgnore
    @Override
    public String getClassName() {
        return "Genes and Transcripts";
    }
}
