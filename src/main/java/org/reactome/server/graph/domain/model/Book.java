package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class Book extends Publication {

    @ReactomeProperty
    private String ISBN;
    @ReactomeProperty
    private String chapterTitle;
    @ReactomeProperty
    private String pages;
    @ReactomeProperty
    private Integer year;

    @Relationship(type = "publisher", direction = Relationship.OUTGOING)
    private Affiliation publisher;

    public Book() {}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Affiliation getPublisher() {
        return publisher;
    }

    public void setPublisher(Affiliation publisher) {
        this.publisher = publisher;
    }
}
