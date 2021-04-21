package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class EvidenceType extends DatabaseObject {

    @ReactomeProperty
    private String definition;

    public EvidenceType() {}

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

}
