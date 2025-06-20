package org.reactome.server.graph.domain.model;

import java.util.List;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Relationship;

public class PathwayDiagram extends DatabaseObject {
    
    @Relationship(type = "representedPathway")
    private List<Pathway> representedPathway;
    @Relationship(type = "renderedInstance")
    private List<DatabaseObject> renderedInstance;
    
    @ReactomeProperty(addedField = true)
    private String jsonFile; // JSON representation of the pathway diagram
    @ReactomeProperty
    private int width; // Width of the diagram  
    @ReactomeProperty
    private int height; // Height of the diagram
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PathwayDiagram() {
        super();
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public List<Pathway> getRepresentedPathway() {
        return representedPathway;
    }
    
    public void setRepresentedPathway(List<Pathway> representedPathway) {
        this.representedPathway = representedPathway;
    }

    public List<DatabaseObject> getRenderedInstance() {
        return renderedInstance;
    }
    
    public void setRenderedInstance(List<DatabaseObject> renderedInstance) {
        this.renderedInstance = renderedInstance;
    }

}
