package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Book extends Publication {

    private String isbn;
    private String chapterTitle;
    private String pages;
    private Integer publisher;
    private String publisherClass;
    private Integer year;

    public Book() {}

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getChapterTitle() {
        return this.chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getPages() {
        return this.pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublisherClass() {
        return this.publisherClass;
    }

    public void setPublisherClass(String publisherClass) {
        this.publisherClass = publisherClass;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
