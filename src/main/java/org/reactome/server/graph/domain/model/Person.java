package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.AuthorPublication;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Node
public class Person extends DatabaseObject {

    @ReactomeProperty
    private String firstname;
    @ReactomeProperty
    private String initial;
    @ReactomeProperty(addedField = true)
    private String orcidId;
    @ReactomeProperty
    private String project;
    @ReactomeProperty
    private String surname;

    @Relationship(type = "affiliation")
    private List<Affiliation> affiliation;

    @Deprecated
    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @ReactomeTransient
    @Relationship(type = "author")
    private List<AuthorPublication> publicationAuthorList;

    public Person() {}

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

    @JsonGetter("publications")
    public List<Publication> getPublications() {
        if (publicationAuthorList == null) return null;
        List<Publication> rtn = new ArrayList<>();
        for (AuthorPublication publicationAuthor : publicationAuthorList) {
            rtn.add(publicationAuthor.getPublication());
        }
        return rtn;
    }
}
