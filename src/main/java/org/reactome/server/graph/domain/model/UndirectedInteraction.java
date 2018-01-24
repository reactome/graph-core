package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * The name of the has been taken from Cytoscape.
 *
 * Undirected interactions has a list of ReferenceEntity instances that MUST contain only 2 elements.
 *
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@NodeEntity
public class UndirectedInteraction extends Interaction {

    @Relationship(type = "interactor")
    private List<ReferenceEntity> interactor;

    public UndirectedInteraction() { }

    public List<ReferenceEntity> getInteractor() {
        return interactor;
    }

    @Relationship(type = "interactor")
    public void setInteractor(List<ReferenceEntity> interactor) {
        this.interactor = interactor;
    }
}
