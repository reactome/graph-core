package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * Two or more entities that are interchangeable in function.
 */
@SuppressWarnings("unused")
@NodeEntity
public class DefinedSet extends EntitySet {

    public DefinedSet() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Two or more entities that are interchangeable in function";
    }
}
