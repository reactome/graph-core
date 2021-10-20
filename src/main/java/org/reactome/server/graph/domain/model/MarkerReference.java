package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class MarkerReference extends ControlReference {

    @Relationship(type = "marker")
    private List<EntityWithAccessionedSequence> marker;

    public MarkerReference() {
    }

    public List<EntityWithAccessionedSequence> getMarker() {
        return marker;
    }

    public void setMarker(List<EntityWithAccessionedSequence> marker) {
        this.marker = marker;
    }

}