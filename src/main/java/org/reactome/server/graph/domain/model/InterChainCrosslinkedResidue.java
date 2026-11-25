package org.reactome.server.graph.domain.model;

import java.util.List;
import java.util.Set;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class InterChainCrosslinkedResidue extends CrosslinkedResidue {
    private static final long serialVersionUID = 1L;

    @Relationship(type = "equivalentTo")
    private List<InterChainCrosslinkedResidue> equivalentTo;

    @Relationship(type = "secondReferenceSequence")
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
