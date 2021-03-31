package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Two or more entities that are interchangeable in function.
 */
@SuppressWarnings("unused")
@Node
public class DefinedSet extends EntitySet {

    public DefinedSet() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Two or more entities that are interchangeable in function";
    }
}
