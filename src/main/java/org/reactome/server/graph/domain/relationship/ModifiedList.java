package org.reactome.server.graph.domain.relationship;

import java.util.Objects;

import org.reactome.server.graph.domain.model.InstanceEdit;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ModifiedList implements Comparable<ModifiedList> {
    @Id @GeneratedValue private Long id;
    @TargetNode private InstanceEdit instanceEdit;

    private int order;
    
    public ModifiedList() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstanceEdit getInstanceEdit() {
        return instanceEdit;
    }

    public void setInstanceEdit(InstanceEdit instanceEdit) {
        this.instanceEdit = instanceEdit;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(instanceEdit, ((ModifiedList) o).instanceEdit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceEdit);
    }

    @Override
    public int compareTo(ModifiedList o) {
        return this.order - o.order;
    }

}
