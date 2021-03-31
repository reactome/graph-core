package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

/**
 * Reactions that follow the pattern: Polymer - Polymer + Unit  (there may be a catalyst involved). Used to describe the mechanistic detail of depolymerisation
 */
@SuppressWarnings("unused")
@Node
public class Depolymerisation extends ReactionLikeEvent {

    public Depolymerisation() {}

}
