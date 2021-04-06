package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "IntraChainCrosslinkedResidue")
public class IntraChainCrosslinkedResidue extends CrosslinkedResidue {
    
    public IntraChainCrosslinkedResidue() {}
    
}
