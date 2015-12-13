package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class InstanceEdit extends DatabaseObject {

    private String applyToAllEditedInstances;
    private String dateTime;
    private String note;
    @Relationship
    private List<Person> author;

    public InstanceEdit() {
    }

    public String getApplyToAllEditedInstances() {
        return this.applyToAllEditedInstances;
    }

    public void setApplyToAllEditedInstances(String applyToAllEditedInstances) {
        this.applyToAllEditedInstances = applyToAllEditedInstances;
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
