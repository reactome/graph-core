package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class DiagramOccurrences {

    private Pathway diagram;

    private boolean inDiagram;

    private List<Pathway> subpathways;

    public Pathway getDiagram() {
        return diagram;
    }

    public void setDiagram(Pathway diagram) {
        this.diagram = diagram;
    }

    public boolean isInDiagram() {
        return inDiagram;
    }

    public void setInDiagram(boolean inDiagram) {
        this.inDiagram = inDiagram;
    }

    public List<Pathway> getSubpathways() {
        return subpathways;
    }

    public void setSubpathways(List<Pathway> subpathways) {
        this.subpathways = subpathways;
    }
}
