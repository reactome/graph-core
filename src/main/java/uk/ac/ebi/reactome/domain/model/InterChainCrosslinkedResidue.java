package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class InterChainCrosslinkedResidue extends CrosslinkedResidue {

    @Relationship
    private List<InterChainCrosslinkedResidue> equivalentTo;
    @Relationship
    private List<ReferenceSequence> secondReferenceSequence;
    
    public InterChainCrosslinkedResidue() {
        
    }

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
