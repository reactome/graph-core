package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class URL extends Publication {

    @ReactomeProperty
    private String uniformResourceLocator;

    public URL() {}

    public String getUniformResourceLocator() {
        return this.uniformResourceLocator;
    }

    public void setUniformResourceLocator(String uniformResourceLocator) {
        this.uniformResourceLocator = uniformResourceLocator;
    }

}
