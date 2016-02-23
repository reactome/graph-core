package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class FragmentModification extends GeneticallyModifiedResidue {

    private Integer endPositionInReferenceSequence;
    private Integer startPositionInReferenceSequence;
    
    public FragmentModification() {}

    public Integer getEndPositionInReferenceSequence() {
        return endPositionInReferenceSequence;
    }

    public void setEndPositionInReferenceSequence(Integer endPositionInReferenceSequence) {
        this.endPositionInReferenceSequence = endPositionInReferenceSequence;
    }

    public Integer getStartPositionInReferenceSequence() {
        return startPositionInReferenceSequence;
    }

    public void setStartPositionInReferenceSequence(Integer startPositionInReferenceSequence) {
        this.startPositionInReferenceSequence = startPositionInReferenceSequence;
    }
    
    
    
}
