package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class LiteratureReference extends Publication {

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

}
