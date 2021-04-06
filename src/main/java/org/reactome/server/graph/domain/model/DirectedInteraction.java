package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * The name of the has been taken from Cytoscape.
 *
 * Directed interactions has two properties holding a ReferenceEntity instance each. The interaction is
 *
 *   (source) -[interacts]-> (target)
 *
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@Node(primaryLabel = "DirectedInteraction")
public class DirectedInteraction extends Interaction {

    @Relationship(type = "source")
    private ReferenceEntity source;

    @Relationship(type = "target")
    private ReferenceEntity target;

    public DirectedInteraction() { }

    public ReferenceEntity getSource() {
        return source;
    }

    public void setSource(ReferenceEntity source) {
        this.source = source;
    }

    public ReferenceEntity getTarget() {
        return target;
    }

    public void setTarget(ReferenceEntity target) {
        this.target = target;
    }
}
