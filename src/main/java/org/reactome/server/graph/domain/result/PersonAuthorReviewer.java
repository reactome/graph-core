package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Person;

public class PersonAuthorReviewer {

    private Person person;
    private Long authoredPathways;
    private Long reviewedPathways;
    private Long authoredReactions;
    private Long reviewedReactions;

    public PersonAuthorReviewer(Person person, Long authoredPathways, Long reviewedPathways, Long authoredReactions, Long reviewedReactions) {
        this.person = person;
        this.authoredPathways = authoredPathways;
        this.reviewedPathways = reviewedPathways;
        this.authoredReactions = authoredReactions;
        this.reviewedReactions = reviewedReactions;
    }

    public Person getPerson() {
        return person;
    }

    public Long getAuthoredPathways() {
        return authoredPathways;
    }

    public Long getReviewedPathways() {
        return reviewedPathways;
    }

    public Long getAuthoredReactions() {
        return authoredReactions;
    }

    public Long getReviewedReactions() {
        return reviewedReactions;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setAuthoredPathways(Long authoredPathways) {
        this.authoredPathways = authoredPathways;
    }

    public void setReviewedPathways(Long reviewedPathways) {
        this.reviewedPathways = reviewedPathways;
    }

    public void setAuthoredReactions(Long authoredReactions) {
        this.authoredReactions = authoredReactions;
    }

    public void setReviewedReactions(Long reviewedReactions) {
        this.reviewedReactions = reviewedReactions;
    }

}
