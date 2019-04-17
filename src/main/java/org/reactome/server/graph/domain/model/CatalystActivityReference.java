package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CatalystActivityReference extends ControlReference {

    @Relationship(type = "catalystActivity")
    private CatalystActivity catalystActivity;

    public CatalystActivityReference() {
    }

    public CatalystActivity getCatalystActivity() {
        return catalystActivity;
    }

    @Relationship(type = "catalystActivity")
    public void setCatalystActivity(CatalystActivity catalystActivity) {
        this.catalystActivity = catalystActivity;
    }

}
