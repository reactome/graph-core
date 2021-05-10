package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class DrugActionType extends ReactionType {

    @Relationship(type = "instanceOf")
    private List<DrugActionType> instanceOf;

    public DrugActionType() {
    }

    public List<DrugActionType> getInstanceOf() {
        return instanceOf;
    }

    @Relationship(type = "instanceOf")
    public void setInstanceOf(List<DrugActionType> instanceOf) {
        this.instanceOf = instanceOf;
    }
}
