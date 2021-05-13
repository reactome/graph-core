package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@Node
public class InstanceEdit extends DatabaseObject {

    @ReactomeProperty
    private String dateTime;
    @ReactomeProperty
    private String note;

    @Relationship(type = "author", direction = Relationship.Direction.INCOMING)
    private List<Person> author;

    public InstanceEdit() {}

    public InstanceEdit(Long dbId) {
        super(dbId);
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Person> getAuthor() {
        return author;
    }

    public void setAuthor(List<Person> author) {
        this.author = author;
    }

}
