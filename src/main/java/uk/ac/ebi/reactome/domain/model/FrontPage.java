package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class FrontPage extends DatabaseObject {

    @Relationship(type = "frontPageItem")
    private List<Event> frontPageItem;

    public FrontPage() {}

    public List<Event> getFrontPageItem() {
        return frontPageItem;
    }

    public void setFrontPageItem(List<Event> frontPageItem) {
        this.frontPageItem = frontPageItem;
    }
}
