package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class InteractionEvent extends Event {

    @Relationship(type = "partners")
    private List<PhysicalEntity> partners;

    public InteractionEvent() { }

    public List<PhysicalEntity> getPartners() {
        return partners;
    }

    @Relationship(type = "partners")
    public void setPartners(List<PhysicalEntity> partners) {
        this.partners = partners;
    }

}