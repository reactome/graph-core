package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class LiteratureReference extends Publication {

    private String journal;
    private String pages;
    private Integer pubMedIdentifier;
    private Integer volume;
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
