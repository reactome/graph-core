package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Species extends Taxon {

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    private List<Figure> figure;

    private String abbreviation;

    public Species() {}

    public List<Figure> getFigure() {
        return figure;
    }

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
