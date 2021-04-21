package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class Book extends Publication {

    @ReactomeProperty
    private String ISBN;
    @ReactomeProperty
    private String chapterTitle;
    @ReactomeProperty
    private String pages;
    @ReactomeProperty
    private Integer year;

    @Relationship(type = "publisher")
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
