package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class PositiveGeneExpressionRegulation extends PositiveRegulation {

    public PositiveGeneExpressionRegulation() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A positive gene expression regulation";
    }
}
