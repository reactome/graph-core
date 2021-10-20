package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class RegulationReference extends ControlReference {

    @Relationship(type = "regulation")
    private Regulation regulation;

    public RegulationReference() {
    }

    public Regulation getRegulatedBy() {
        return regulation;
    }

    public void setRegulatedBy(Regulation regulation) {
        this.regulation = regulation;
    }
}
