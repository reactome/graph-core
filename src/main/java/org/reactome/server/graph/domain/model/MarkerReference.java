package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class MarkerReference extends ControlReference {

    @Relationship(type = Relationships.MARKER)
    private EntityWithAccessionedSequence marker;

    @Relationship(type = Relationships.CELL)
    private List<Cell> cell;

    public MarkerReference() {
    }

    public EntityWithAccessionedSequence getMarker() {
        return marker;
    }

    public void setMarker(EntityWithAccessionedSequence marker) {
        this.marker = marker;
    }

    public List<Cell> getCell() {
        return cell;
    }

    public void setCell(List<Cell> cell) {
        this.cell = cell;
    }
}