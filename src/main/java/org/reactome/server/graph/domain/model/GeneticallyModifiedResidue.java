package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "GeneticallyModifiedResidue")
public class GeneticallyModifiedResidue extends AbstractModifiedResidue {
    
    public GeneticallyModifiedResidue() {}
    
}
