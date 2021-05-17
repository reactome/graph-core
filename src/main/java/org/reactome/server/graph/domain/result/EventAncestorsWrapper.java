package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Event;

import java.util.Collection;

public class EventAncestorsWrapper {

    private Collection<Event> nodes;

    public EventAncestorsWrapper(Collection<Event> aNodes) {
        this.nodes = aNodes;
    }

    public Collection<Event> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Event> nodes) {
        this.nodes = nodes;
    }
}

