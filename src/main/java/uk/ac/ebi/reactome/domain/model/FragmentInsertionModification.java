package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class FragmentInsertionModification extends FragmentModification {

    private Integer coordinate;
    
    public FragmentInsertionModification() {}

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }
    
}
