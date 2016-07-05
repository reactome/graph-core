package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Publication extends DatabaseObject {

    @ReactomeProperty
    private String title;

    @Relationship(type = "author", direction = Relationship.INCOMING)
    private List<Person> author;

    public Publication() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Person> getAuthor() {
        return author;
    }

    public void setAuthor(List<Person> author) {
        this.author = author;
    }
}
