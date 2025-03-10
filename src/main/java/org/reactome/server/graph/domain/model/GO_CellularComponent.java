package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.management.relation.Relation;
import java.util.List;

@SuppressWarnings("unused")
@Node
public class GO_CellularComponent extends GO_Term {

    @Relationship(type = Relationships.COMPONENT_OF)
    private List<GO_CellularComponent> componentOf;

    @Relationship(type = Relationships.HAS_PART)
    private List<GO_CellularComponent> hasPart;

    @Relationship(type = Relationships.INSTANCE_OF)
    private List<GO_CellularComponent> instanceOf;

    @Relationship(type = Relationships.SURROUNDED_BY)
    private List<GO_CellularComponent> surroundedBy;

    public GO_CellularComponent() {}

    public GO_CellularComponent(Long dbId) {
        super(dbId);
    }

    public List<GO_CellularComponent> getComponentOf() {
        return componentOf;
    }

    public void setComponentOf(List<GO_CellularComponent> componentOf) {
        this.componentOf = componentOf;
    }

    public List<GO_CellularComponent> getHasPart() {
        return hasPart;
    }

    public void setHasPart(List<GO_CellularComponent> hasPart) {
        this.hasPart = hasPart;
    }

    public List<GO_CellularComponent> getInstanceOf() {
        return instanceOf;
    }

    public void setInstanceOf(List<GO_CellularComponent> instanceOf) {
        this.instanceOf = instanceOf;
    }

    public List<GO_CellularComponent> getSurroundedBy() {
        return surroundedBy;
    }

    public void setSurroundedBy(List<GO_CellularComponent> surroundedBy) {
        this.surroundedBy = surroundedBy;
    }
}
