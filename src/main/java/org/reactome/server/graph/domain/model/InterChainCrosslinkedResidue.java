package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@Node
public class InterChainCrosslinkedResidue extends CrosslinkedResidue {

    @Relationship(type = "equivalentTo")
    private List<InterChainCrosslinkedResidue> equivalentTo;

    @Relationship(type = "secondReferenceSequence")
    private List<ReferenceSequence> secondReferenceSequence;
    
    public InterChainCrosslinkedResidue() {}

    public List<InterChainCrosslinkedResidue> getEquivalentTo() {
        return equivalentTo;
    }

    public void setEquivalentTo(List<InterChainCrosslinkedResidue> equivalentTo) {
        this.equivalentTo = equivalentTo;
    }

    public List<ReferenceSequence> getSecondReferenceSequence() {
        return secondReferenceSequence;
    }

    public void setSecondReferenceSequence(List<ReferenceSequence> secondReferenceSequence) {
        this.secondReferenceSequence = secondReferenceSequence;
    }
}
