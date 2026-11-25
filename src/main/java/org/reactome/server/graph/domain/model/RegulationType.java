package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@Deprecated
@SuppressWarnings("unused")
@Node
public class RegulationType extends DatabaseObject {
    private static final long serialVersionUID = 1L;

    @ReactomeProperty
    private String name;

    public RegulationType() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
