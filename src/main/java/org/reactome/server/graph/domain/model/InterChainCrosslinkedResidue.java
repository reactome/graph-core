package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Node
public class InterChainCrosslinkedResidue extends CrosslinkedResidue {

    @Relationship(type = Relationships.EQUIVALENT_TO)
    private List<InterChainCrosslinkedResidue> equivalentTo;

    @Relationship(type = Relationships.SECOND_REFERENCE_SEQUENCE)
    private Set<ReferenceSequence> secondReferenceSequence;
    
    public InterChainCrosslinkedResidue() {}

    public List<InterChainCrosslinkedResidue> getEquivalentTo() {
        return equivalentTo;
    }

    public void setEquivalentTo(List<InterChainCrosslinkedResidue> equivalentTo) {
        this.equivalentTo = equivalentTo;
    }

    public Set<ReferenceSequence> getSecondReferenceSequence() {
        return secondReferenceSequence;
    }

    public void setSecondReferenceSequence(Set<ReferenceSequence> secondReferenceSequence) {
        this.secondReferenceSequence = secondReferenceSequence;
    }
}
