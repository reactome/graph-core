package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * The name of the has been taken from Cytoscape.
 *
 * Undirected interactions has a list of ReferenceEntity instances that MUST contain only 2 elements.
 *
 */
@SuppressWarnings("unused")
@Node
public class UndirectedInteraction extends Interaction {

    @Relationship(type = "interactor")
    private List<ReferenceEntity> interactor;

    public UndirectedInteraction() { }

    public List<ReferenceEntity> getInteractor() {
        return interactor;
    }

    public void setInteractor(List<ReferenceEntity> interactor) {
        this.interactor = interactor;
    }
}
