package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;

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
