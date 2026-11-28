package org.reactome.server.graph.domain.model;

import java.util.List;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class ReferenceRNASequence extends ReferenceSequence {
    private static final long serialVersionUID = 1L;

    @Relationship(type = "referenceGene")
    private List<ReferenceDNASequence> referenceGene;

    public ReferenceRNASequence() {
        super();
    }

    public List<ReferenceDNASequence> getReferenceGene() {
        return referenceGene;
    }

    public void setReferenceGene(List<ReferenceDNASequence> referenceGene) {
        this.referenceGene = referenceGene;
    }
}
