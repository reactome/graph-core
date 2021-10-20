package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class Compartment extends GO_CellularComponent {

    public Compartment() {}
    public Compartment(Long dbId) {
        super(dbId);
    }
}
