package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class PersonAuthorReviewer {

    private Person person;
    private Long authoredPathways;
    private Long authoredReactions;
    private Long reviewedPathways;
    private Long reviewedReactions;


    public Person getPerson() {
        return person;
    }

    public Long getAuthoredPathways() {
        return authoredPathways;
    }

    public Long getAuthoredReactions() {
        return authoredReactions;
    }

    public Long getReviewedPathways() {
        return reviewedPathways;
    }

    public Long getReviewedReactions() {
        return reviewedReactions;
    }
}
