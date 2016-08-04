package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

@SuppressWarnings("unused")
@NodeEntity
public class Requirement extends PositiveRegulation {

    public Requirement() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A regulator that is required for an Event/CatalystActivity to happen";
    }
}
