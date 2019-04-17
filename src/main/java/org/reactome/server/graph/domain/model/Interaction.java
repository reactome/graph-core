package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Interaction extends DatabaseObject {

    @Relationship(type = "referenceDatabase")
    private ReferenceDatabase referenceDatabase;
    @ReactomeProperty
    private String databaseName;
    @ReactomeProperty
    private Double score;
    @ReactomeProperty
    private List<String> accession;
    @ReactomeProperty
    private List<String> pubmed;
    @ReactomeProperty
    private String url;

//    private List<String> type;
//    private String expansionType; //spokeExpansion

    public Interaction() { }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    @Relationship(type = "referenceDatabase")
    public void setReferenceDatabase(ReferenceDatabase referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getAccession() {
        return accession;
    }

    public void setAccession(List<String> accession) {
        this.accession = accession;
    }

    public List<String> getPubmed() {
        return pubmed;
    }

    public void setPubmed(List<String> pubmed) {
        this.pubmed = pubmed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
