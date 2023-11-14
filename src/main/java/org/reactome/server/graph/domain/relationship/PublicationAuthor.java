package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Person;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

@RelationshipProperties
public class PublicationAuthor implements Comparable<PublicationAuthor> {
    @Id @GeneratedValue private Long id;
    @TargetNode private Person person;

    private int order;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(person, ((PublicationAuthor) o).person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }

    @Override
    public int compareTo(PublicationAuthor o) {
        return this.order - o.order;
    }
}