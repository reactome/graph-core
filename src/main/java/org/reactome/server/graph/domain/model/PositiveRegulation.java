package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * This describes an Event/CatalystActivity that is positively regulated by the Regulator (e.g., allosteric activation).
 */
@SuppressWarnings("unused")
@Node
public class PositiveRegulation extends Regulation {
    private static final long serialVersionUID = 1L;

    public PositiveRegulation() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "This describes an Event/CatalystActivity that is positively regulated by the Regulator (e.g., allosteric activation)";
    }
}
