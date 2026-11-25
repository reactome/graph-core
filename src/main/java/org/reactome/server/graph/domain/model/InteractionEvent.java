package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class InteractionEvent extends Event {
    private static final long serialVersionUID = 1L;

    @Relationship(type = "partners")
    private List<PhysicalEntity> partners;

    public InteractionEvent() { }

    public List<PhysicalEntity> getPartners() {
        return partners;
    }

    public void setPartners(List<PhysicalEntity> partners) {
        this.partners = partners;
    }

}