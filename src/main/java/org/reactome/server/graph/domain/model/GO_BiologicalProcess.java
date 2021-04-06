package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "GO_BiologicalProcess")
public class GO_BiologicalProcess extends GO_Term{

    public GO_BiologicalProcess() {}

}
