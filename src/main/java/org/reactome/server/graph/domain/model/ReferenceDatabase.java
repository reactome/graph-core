package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@Node
public class ReferenceDatabase extends DatabaseObject {

    @ReactomeProperty
    private String accessUrl;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String url;
    @ReactomeProperty
    private String resourceIdentifier;

    public ReferenceDatabase() {}

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public void setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

}
