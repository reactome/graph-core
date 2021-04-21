package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@Node
public class NegativeGeneExpressionRegulation extends NegativeRegulation {

    public NegativeGeneExpressionRegulation() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A negative gene expression regulation";
    }
}
