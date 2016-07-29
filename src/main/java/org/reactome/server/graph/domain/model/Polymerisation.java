package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * Reactions that follow the pattern: Polymer + Unit - Polymer (there may be a catalyst involved). Used to describe the mechanistic detail of a polymerisation
 */
@SuppressWarnings("unused")
@NodeEntity
public class Polymerisation extends ReactionLikeEvent {

    public Polymerisation() {}

    @ReactomeSchemaIgnore
    @Override
    public String getExplanation() {
        return "Reactions that follow the pattern: Polymer + Unit -> Polymer (there may be a catalyst involved). " +
                "Used to describe the mechanistic detail of a polymerisation";
    }
}
