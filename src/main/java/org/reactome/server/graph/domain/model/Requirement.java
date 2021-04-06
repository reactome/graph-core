package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node(primaryLabel = "Requirement")
public class Requirement extends PositiveRegulation {

    public Requirement() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A regulator that is required for an Event/CatalystActivity to happen";
    }
}
