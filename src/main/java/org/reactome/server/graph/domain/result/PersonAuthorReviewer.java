package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
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

    public static PersonAuthorReviewer build(Record record) {
        PersonAuthorReviewer par = new PersonAuthorReviewer();
        par.setPerson(new PersonDTO(record.get("person")));
        par.setAuthoredPathways(record.get("authoredPathways").asLong());
        par.setAuthoredReactions(record.get("authoredReactions").asLong());
        par.setReviewedPathways(record.get("reviewedPathways").asLong());
        par.setReviewedReactions(record.get("reviewedReactions").asLong());
        return par;
    }
}
