package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.relationship.PublicationAuthor;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings("unused")
@NodeEntity
public class Publication extends DatabaseObject {

    @ReactomeProperty
    private String title;

    @Relationship(type = "author", direction = Relationship.INCOMING)
    private SortedSet<PublicationAuthor> author;

    public Publication() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Relationship(type = "author", direction = Relationship.INCOMING)
    public List<Person> getAuthor() {
        if (author == null) return null;
        List<Person> rtn = new ArrayList<>();
        for (PublicationAuthor author : author) {
            rtn.add(author.getAuthor());
        }
        return rtn;
    }

    @Relationship(type = "author", direction = Relationship.INCOMING)
    public void setAuthor(List<Person> author) {
        this.author = new TreeSet<>();
        int order = 0;
        for (Person person : author) {
            PublicationAuthor aux = new PublicationAuthor();
            aux.setAuthor(person);
            aux.setPublication(this);
            aux.setOrder(order++);
            this.author.add(aux);
        }
    }
}
