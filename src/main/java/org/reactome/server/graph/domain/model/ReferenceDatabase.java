package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class ReferenceDatabase extends DatabaseObject {

    @ReactomeProperty
    private String accessUrl;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String url;
    
    public ReferenceDatabase() {}

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessurl) {
        this.accessUrl = accessurl;
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
    
}
