package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Person;



public class PersonAuthorReviewer {

    private Person person;
    private Long authoredPathways;
    private Long reviewedPathways;
    private Long authoredReactions;
    private Long reviewedReactions;


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
}
