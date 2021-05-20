package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class DrugActionType extends ReactionType {

    @Relationship(type = "instanceOf")
    private List<DrugActionType> instanceOf;

    public DrugActionType() {
    }

    public List<DrugActionType> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<DrugActionType> instanceOf) {
        this.instanceOf = instanceOf;
    }
}
