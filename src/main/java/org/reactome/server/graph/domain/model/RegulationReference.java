package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class RegulationReference extends ControlReference {

    @Relationship(type = "regulation")
    private Regulation regulation;

    public RegulationReference() {
    }

    public Regulation getRegulatedBy() {
        return regulation;
    }

    @Relationship(type = "regulation")
    public void setRegulatedBy(Regulation regulation) {
        this.regulation = regulation;
    }
}
