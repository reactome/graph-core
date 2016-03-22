package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class FragmentInsertionModification extends FragmentModification {

    @ReactomeProperty
    private Integer coordinate;
    
    public FragmentInsertionModification() {}

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }
    
}
