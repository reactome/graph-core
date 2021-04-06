package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@SuppressWarnings("unused")
@Node(primaryLabel = "FunctionalStatusType")
public class FunctionalStatusType extends DatabaseObject {

    @ReactomeProperty
    private String definition;
    @ReactomeProperty
    private List<String> name;

    public FunctionalStatusType() {}

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

}
