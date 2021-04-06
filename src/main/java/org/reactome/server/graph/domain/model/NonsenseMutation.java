package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@Node(primaryLabel = "NonsenseMutation")
public class NonsenseMutation extends ReplacedResidue {

    public NonsenseMutation() { }
}