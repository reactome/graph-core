package org.reactome.server.graph.domain.result;

import java.util.List;

public class DiagramResult {

    private String diagramStId;
    private List<String> events;
    private Integer width;
    private Integer height;

    public DiagramResult() { }

    public DiagramResult(String diagramStId, List<String> events, Integer width, Integer height) {
        this.diagramStId = diagramStId;
        this.events = events;
        this.width = width;
        this.height = height;
    }

    public String getDiagramStId() {
        return diagramStId;
    }

    public void setDiagramStId(String diagramStId) {
        this.diagramStId = diagramStId;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize(){
        return width * height;
    }
}
