package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;

/**
 * @author Luanne Misquitta
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@RelationshipEntity(type = "author")
public class PublicationAuthor implements Comparable {

    @SuppressWarnings("unused")
    @JsonIgnore
    @GraphId
    private Long id;

    @StartNode
    private Person author;

    @EndNode
    private Publication publication;

    private int order;

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(Object o) {
        return this.order - ((PublicationAuthor) o).order;
    }
}