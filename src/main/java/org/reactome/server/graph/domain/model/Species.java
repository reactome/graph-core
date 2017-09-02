package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Species extends Taxon {

    @Relationship(type = "figure")
    private List<Figure> figure;

    @ReactomeProperty
    private String abbreviation;

    public Species() {}

    public List<Figure> getFigure() {
        return figure;
    }

    @Relationship(type = "figure")
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
