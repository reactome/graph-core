package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Reactions that follow the pattern: Polymer + Unit - Polymer (there may be a catalyst involved). Used to describe the mechanistic detail of a polymerisation
 */
@SuppressWarnings("unused")
@Node
public class Polymerisation extends ReactionLikeEvent {

    public Polymerisation() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Reactions that follow the pattern: Polymer + Unit -> Polymer (there may be a catalyst involved). " +
                "Used to describe the mechanistic detail of a polymerisation";
    }
}
