package org.reactome.server.graph.domain.model;

import java.util.List;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class FunctionalStatusType extends DatabaseObject {
    private static final long serialVersionUID = 1L;

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
