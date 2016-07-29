package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Person extends DatabaseObject {

    //should not be visible to the public
    @JsonIgnore
    @ReactomeProperty
    private String eMailAddress;
    @ReactomeProperty
    private String firstname;
    @ReactomeProperty
    private String initial;
    @ReactomeProperty
    private String orcidId;
    @ReactomeProperty
    private String project;
    @ReactomeProperty
    private String surname;

    @Relationship(type = "affiliation", direction = Relationship.OUTGOING)
    private List<Affiliation> affiliation;

    @Deprecated
    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    private List<DatabaseIdentifier> crossReference;

//    @ReactomeTransient
//    @Relationship(type = "author", direction = Relationship.OUTGOING)
//    private List<Publication> publications;

    public Person() {}

    @ReactomeSchemaIgnore
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

    public String getOrcidId() {
        return orcidId;
    }

    public void setOrcidId(String orcidId) {
        this.orcidId = orcidId;
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

    @Deprecated
    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    @Deprecated
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

//    public List<Publication> getPublications() {
//        return publications;
//    }
//
//    public void setPublications(List<Publication> publications) {
//        this.publications = publications;
//    }
}
