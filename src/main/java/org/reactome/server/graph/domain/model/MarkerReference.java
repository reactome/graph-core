package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class MarkerReference extends ControlReference {

    @Relationship(type = "marker")
    private List<EntityWithAccessionedSequence> marker;

    public MarkerReference() {
    }

    public List<EntityWithAccessionedSequence> getMarker() {
        return marker;
    }

    @Relationship(type = "marker")
    public void setMarker(List<EntityWithAccessionedSequence> marker) {
        this.marker = marker;
    }

}