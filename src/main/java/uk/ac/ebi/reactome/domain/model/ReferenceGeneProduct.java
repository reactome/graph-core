package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class ReferenceGeneProduct extends ReferenceSequence {
    @Relationship
    private List<ReferenceDNASequence> referenceGene;
    @Relationship
    private List<ReferenceRNASequence> referenceTranscript;

    public ReferenceGeneProduct() {
    }

    public List<ReferenceDNASequence> getReferenceGene() {
        return referenceGene;
    }

    public void setReferenceGene(List<ReferenceDNASequence> referenceGene) {
        this.referenceGene = referenceGene;
    }

    public List<ReferenceRNASequence> getReferenceTranscript() {
        return referenceTranscript;
    }

    public void setReferenceTranscript(List<ReferenceRNASequence> referenceTranscript) {
        this.referenceTranscript = referenceTranscript;
    }

    
}
