package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Collection;

@QueryResult
public class PathwayResult {
    private String displayName;
    private String doi;
    private String stId;
    private String species;
    private String releaseDate;

    private String reviseDate;
    private String releaseStatus;


    private Collection<Person> authors;
    private Collection<Person> reviewers;
    private Collection<Person> editors;


    private Collection<Pathway> subPathway;

    public PathwayResult() {
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

    public Collection<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Person> authors) {
        this.authors = authors;
    }

    public Collection<Person> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Collection<Person> reviewers) {
        this.reviewers = reviewers;
    }

    public Collection<Person> getEditors() {
        return editors;
    }

    public void setEditors(Collection<Person> editors) {
        this.editors = editors;
    }

    public Collection<Pathway> getSubPathway() {
        return subPathway;
    }

    public void setSubPathway(Collection<Pathway> subPathway) {
        this.subPathway = subPathway;
    }
}

