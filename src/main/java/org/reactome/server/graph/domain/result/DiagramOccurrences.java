package org.reactome.server.graph.domain.result;

import java.util.List;

public class DiagramOccurrences {

    private String diagramStId;
    private Boolean inDiagram;
    private List<String> occurrences;
    private List<String> interactsWith;

    public DiagramOccurrences() { }

    public DiagramOccurrences(String diagramStId, Boolean inDiagram, List<String> occurrences, List<String> interactsWith) {
        this.diagramStId = diagramStId;
        this.inDiagram = inDiagram;
        this.occurrences = occurrences;
        this.interactsWith = interactsWith;
    }

    public String getDiagramStId() {
        return diagramStId;
    }

    public Boolean isInDiagram() {
        return inDiagram;
    }

    public List<String> getOccurrences() {
        return occurrences;
    }

    public List<String> getInteractsWith() {
        return interactsWith;
    }
}
