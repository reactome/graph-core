package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceRNASequence extends ReferenceSequence {

    @Relationship(type = "referenceGene", direction = Relationship.OUTGOING)
    private List<ReferenceDNASequence> referenceGene;
    
    public ReferenceRNASequence() {}

    public List<ReferenceDNASequence> getReferenceGene() {
        return referenceGene;
    }

    public void setReferenceGene(List<ReferenceDNASequence> referenceGene) {
        this.referenceGene = referenceGene;
    }
    
}
