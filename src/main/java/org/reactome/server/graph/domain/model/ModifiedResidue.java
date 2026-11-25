package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class ModifiedResidue extends TranslationalModification {
    private static final long serialVersionUID = 1L;
    
    public ModifiedResidue() {}

    public ModifiedResidue(Long dbId) {
        super(dbId);
    }
    
}
