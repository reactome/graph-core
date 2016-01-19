package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Book extends Publication {

    private String isbn;
    private String chapterTitle;
    private String pages;
    private Integer year;

    @Relationship(type = "publisher")
    private Affiliation publisher;

    public Book() {}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
