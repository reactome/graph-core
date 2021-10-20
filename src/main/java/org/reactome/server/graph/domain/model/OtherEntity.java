package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Entities that we are unable or unwilling to describe in chemical detail and cannot be put in any other class. Can be used to represent complex structures in the cell that take part in a reaction but which we cannot or do not want to define molecularly, e.g. cell membrane,
 */
@SuppressWarnings("unused")
@Node
public class OtherEntity extends PhysicalEntity {

    public OtherEntity() {}

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "Entities that we are unable or unwilling to describe in chemical detail and cannot be put in any other class. " +
                "Can be used to represent complex structures in the cell that take part in a reaction but which we cannot or do not want to define molecularly, e.g. cell membrane, Holliday structure";
    }
}
