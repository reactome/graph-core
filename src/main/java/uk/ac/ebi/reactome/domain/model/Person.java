package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Person extends DatabaseObject {

    private String eMailAddress;
    private String firstname;
    private String initial;
    private String project;
    private String surname;

    @Relationship(type = "affiliation", direction = Relationship.OUTGOING)
    private List<Affiliation> affiliation;

    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    private List<DatabaseIdentifier> crossReference;

    public Person() {}

    public String getEMailAddress() {
        return eMailAddress;
    }

    public void setEMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Affiliation> getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(List<Affiliation> affiliation) {
        this.affiliation = affiliation;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }
}
