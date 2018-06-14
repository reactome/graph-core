package org.reactome.server.graph.domain.result;

import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class PersonAuthorReviewer {

    private Person person;
    private Long authored;
    private Long reviewed;

    public Person getPerson() {
        return person;
    }

    public Long getAuthored() {
        return authored;
    }

    public Long getReviewed() {
        return reviewed;
    }
}
