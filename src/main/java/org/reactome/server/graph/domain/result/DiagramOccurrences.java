package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class DiagramOccurrences {

    private Pathway pathway;

    private Pathway subpathway;

    public Pathway getPathway() {
        return pathway;
    }

    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
    }

    public Pathway getSubpathway() {
        return subpathway;
    }

    public void setSubpathway(Pathway subpathway) {
        this.subpathway = subpathway;
    }
}
