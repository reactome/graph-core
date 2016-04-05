package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class FragmentModification extends GeneticallyModifiedResidue {

    @ReactomeProperty
    private Integer endPositionInReferenceSequence;
    @ReactomeProperty
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
