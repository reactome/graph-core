package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class CellDevelopmentStep extends ReactionLikeEvent {

    @Relationship(type = "tissue")
    private Anatomy tissue;

    public CellDevelopmentStep() {

    }

    public Anatomy getTissue() {
        return tissue;
    }

    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

}
