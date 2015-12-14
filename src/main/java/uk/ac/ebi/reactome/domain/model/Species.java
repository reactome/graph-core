package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Species extends Taxon {

    @Relationship(type = "figure")
    private List<Figure> figure;

    public Species() {}

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

}
