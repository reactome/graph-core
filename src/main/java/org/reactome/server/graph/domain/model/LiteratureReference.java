package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class LiteratureReference extends Publication {

    private static final transient String PUBMED_URL = "http://www.ncbi.nlm.nih.gov/pubmed/";

    @ReactomeProperty
    private String journal;
    @ReactomeProperty
    private String pages;
    @ReactomeProperty
    private Integer pubMedIdentifier;
    @ReactomeProperty
    private Integer volume;
    @ReactomeProperty
    private Integer year;

    public LiteratureReference() {}

    public LiteratureReference(Long dbId) {
        super(dbId);
    }

    public String getJournal() {
        return this.journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getPages() {
        return this.pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getPubMedIdentifier() {
        return this.pubMedIdentifier;
    }

    public void setPubMedIdentifier(Integer pubMedIdentifier) {
        this.pubMedIdentifier = pubMedIdentifier;
    }

    public Integer getVolume() {
        return this.volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUrl() {
        if (pubMedIdentifier != null) {
            return PUBMED_URL + pubMedIdentifier;
        }
        return null;
    }
}
