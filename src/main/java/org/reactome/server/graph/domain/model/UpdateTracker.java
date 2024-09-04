package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeRelationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class UpdateTracker extends MetaDatabaseObject {

    @ReactomeProperty
    private List<String> action;

    @Relationship(type = "release")
    @ReactomeRelationship(originName = "_release")
    private Release release;

    @Relationship(type = "updatedInstance")
    private List<Trackable> updatedInstance;

    public UpdateTracker() {
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public List<String> getAction() {
        return action;
    }

    public void setAction(List<String> action) {
        this.action = action;
    }

    public List<Trackable> getUpdatedInstance() {
        return updatedInstance;
    }

    public void setUpdatedInstance(List<Trackable> updatedInstance) {
        this.updatedInstance = updatedInstance;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        //todo
        return "updateTracker";
    }
}
