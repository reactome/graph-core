package org.reactome.server.graph.domain.model;

import java.util.List;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class DrugActionType extends ReactionType {
    private static final long serialVersionUID = 1L;

    @Relationship(type = "instanceOf")
    private List<DrugActionType> instanceOf;

    public DrugActionType() {}

    public List<DrugActionType> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<DrugActionType> instanceOf) {
        this.instanceOf = instanceOf;
    }
}
