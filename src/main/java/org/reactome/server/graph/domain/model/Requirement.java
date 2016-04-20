package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class Requirement extends PositiveRegulation {

    public Requirement() {}

    @Override
    public String getExplanation() {
        return "A regulator that is required for an Event/CatalystActivity to happen";
    }
}
