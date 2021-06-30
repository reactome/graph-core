package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
import org.reactome.server.graph.domain.ReflectionUtils;
import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Collection;
import java.util.Collections;

public class DoiPathwayDTO {
    @Id private String stId;
    private String displayName;
    private String doi;
    private String species;
    private String releaseDate;
    private String reviseDate;
    private String releaseStatus;
    private Collection<? extends Person> authors;
    private Collection<? extends Person> reviewers;
    private Collection<? extends Person> editors;

    public DoiPathwayDTO(Record record) {
        displayName = record.get("displayName").asString(null);
        doi = record.get("doi").asString(null);
        stId = record.get("stId").asString(null);
        species = record.get("species").asString(null);
        reviseDate = record.get("reviseDate").asString(null);
        releaseStatus = record.get("releaseStatus").asString(null);
        releaseDate = record.get("releaseDate").asString();
        authors = !record.get("authors").isNull() ? record.get("authors").asList(p -> ReflectionUtils.build(new Person(), p)) : Collections.emptyList();
        reviewers = !record.get("reviewers").isNull() ? record.get("reviewers").asList(p -> ReflectionUtils.build(new Person(), p)) :  Collections.emptyList();
        editors = !record.get("editors").isNull() ? record.get("editors").asList(p -> ReflectionUtils.build(new Person(), p)) :  Collections.emptyList();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReviseDate() {
        return reviseDate;
    }

    public void setReviseDate(String reviseDate) {
        this.reviseDate = reviseDate;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Collection<? extends Person> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Person> authors) {
        this.authors = authors;
    }

    public Collection<? extends Person> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Collection<Person> reviewers) {
        this.reviewers = reviewers;
    }

    public Collection<? extends Person> getEditors() {
        return editors;
    }

    public void setEditors(Collection<Person> editors) {
        this.editors = editors;
    }
}

