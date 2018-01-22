package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class GO_CellularComponent extends GO_Term {

    @Relationship(type = "componentOf")
    private List<GO_CellularComponent> componentOf;

    @Relationship(type = "hasPart")
    private List<GO_CellularComponent> hasPart;

    @Relationship(type = "instanceOf")
    private List<GO_CellularComponent> instanceOf;

    public GO_CellularComponent() {}

    public List<GO_CellularComponent> getComponentOf() {
        return componentOf;
    }

    @Relationship(type = "componentOf")
    public void setComponentOf(List<GO_CellularComponent> componentOf) {
        this.componentOf = componentOf;
    }

    public List<GO_CellularComponent> getHasPart() {
        return hasPart;
    }

    @Relationship(type = "hasPart")
    public void setHasPart(List<GO_CellularComponent> hasPart) {
        this.hasPart = hasPart;
    }

    public List<GO_CellularComponent> getInstanceOf() {
        return instanceOf;
    }

    @Relationship(type = "instanceOf")
    public void setInstanceOf(List<GO_CellularComponent> instanceOf) {
        this.instanceOf = instanceOf;
    }
}
