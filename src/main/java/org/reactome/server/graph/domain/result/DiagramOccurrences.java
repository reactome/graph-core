package org.reactome.server.graph.domain.result;

import java.util.List;

public class DiagramOccurrences {

    private String diagramStId;
    private Boolean inDiagram;
    private Boolean inDiagramFromInteractor;
    private List<String> occurrences;
    private List<String> occurrencesInteractor;
    private List<String> interactsWith;

    public DiagramOccurrences() { }

    public DiagramOccurrences(String diagramStId, Boolean inDiagram, Boolean inDiagramFromInteractor, List<String> occurrences, List<String> occurrencesInteractor, List<String> interactsWith) {
        this.diagramStId = diagramStId;
        this.inDiagram = inDiagram;
        this.inDiagramFromInteractor = inDiagramFromInteractor;
        this.occurrences = occurrences;
        this.occurrencesInteractor = occurrencesInteractor;
        this.interactsWith = interactsWith;
    }

    public String getDiagramStId() {
        return diagramStId;
    }

    public Boolean isInDiagram() {
        return inDiagram;
    }

    public Boolean isInDiagramFromInteractor() {
        return inDiagramFromInteractor;
    }

    public List<String> getOccurrences() {
        return occurrences;
    }

    public List<String> getOccurrencesInteractor() {
        return occurrencesInteractor;
    }

    public List<String> getInteractsWith() {
        return interactsWith;
    }
}
