package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@NodeEntity
public class ControlledVocabulary extends DatabaseObject {

    @ReactomeProperty
    private String definition;

    @ReactomeProperty
    private List<String> name;

    public ControlledVocabulary() {
    }

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
