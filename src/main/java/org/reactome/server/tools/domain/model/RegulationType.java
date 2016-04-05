package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class RegulationType extends DatabaseObject {

    @ReactomeProperty
    private String name;

    public RegulationType() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
