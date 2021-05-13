package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class ModifiedResidue extends TranslationalModification {
    
    public ModifiedResidue() {}

    public ModifiedResidue(Long dbId) {
        super(dbId);
    }
    
}
