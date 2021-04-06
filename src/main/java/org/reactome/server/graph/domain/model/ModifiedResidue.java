package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "ModifiedResidue")
public class ModifiedResidue extends TranslationalModification {
    
    public ModifiedResidue() {}
    
}
