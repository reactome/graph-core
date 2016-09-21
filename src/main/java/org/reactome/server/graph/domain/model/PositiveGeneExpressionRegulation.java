package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@NodeEntity
public class PositiveGeneExpressionRegulation extends PositiveRegulation {

    public PositiveGeneExpressionRegulation() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A positive gene expression regulation";
    }
}
