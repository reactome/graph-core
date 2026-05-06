package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class EventOf extends Has<Pathway> {

    @Override
    public String getType() {
        return "event";
    }

    @JsonIgnore
    public Pathway getPathway() {
        return element;
    }

    public void setPathway(Pathway eventOf) {
        this.element = eventOf;
    }
}
