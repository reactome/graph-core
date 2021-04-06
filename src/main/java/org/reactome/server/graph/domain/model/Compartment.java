package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "Compartment")
public class Compartment extends GO_CellularComponent {

    public Compartment() {}

}
