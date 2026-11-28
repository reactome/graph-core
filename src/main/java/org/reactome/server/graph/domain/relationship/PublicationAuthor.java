package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class PublicationAuthor extends Has<Person> {
    @Override
    public String getType() {
        return "author";
    }

    @JsonIgnore
    public Person getAuthor() {
        return element;
    }

    public void setAuthor(Person author) {
        this.element = author;
    }
}
