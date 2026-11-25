package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@Node
public abstract class ReactionType extends ControlledVocabulary {
    private static final long serialVersionUID = 1L;
}
