package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class CatalystActivityReference extends ControlReference {

    @Relationship(type = "catalystActivity")
    private CatalystActivity catalystActivity;

    public CatalystActivityReference() {
    }

    public CatalystActivity getCatalystActivity() {
        return catalystActivity;
    }

    public void setCatalystActivity(CatalystActivity catalystActivity) {
        this.catalystActivity = catalystActivity;
    }

}
