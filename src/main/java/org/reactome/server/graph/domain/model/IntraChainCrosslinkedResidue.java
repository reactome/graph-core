package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class IntraChainCrosslinkedResidue extends CrosslinkedResidue {
    private static final long serialVersionUID = 1L;
    
    public IntraChainCrosslinkedResidue() {}
    
}
