package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class DiagramOccurrences {

    private Pathway diagram;

    private boolean inDiagram;

    private List<DatabaseObject> occurrences;

    private List<DatabaseObject> interactsWith;

    public Pathway getDiagram() {
        return diagram;
    }

    public boolean isInDiagram() {
        return inDiagram;
    }

    public List<DatabaseObject> getOccurrences() {
        return occurrences;
    }

    public List<DatabaseObject> getInteractsWith() {
        return interactsWith;
    }
}
