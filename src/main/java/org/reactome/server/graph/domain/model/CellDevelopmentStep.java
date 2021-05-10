package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CellDevelopmentStep extends ReactionLikeEvent {

    @Relationship(type = "tissue")
    private Anatomy tissue;

    public CellDevelopmentStep() {

    }

    public Anatomy getTissue() {
        return tissue;
    }

    @Relationship(type = "tissue")
    public void setTissue(Anatomy tissue) {
        this.tissue = tissue;
    }

}
