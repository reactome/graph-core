package org.reactome.server.graph.domain.relationship;

import org.reactome.server.graph.domain.model.Publication;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

@RelationshipProperties
public class AuthorPublication extends Has<Publication> {
    @Override
    public String getType() {
        return "publication";
    }

    public Publication getPublication() {
        return element;
    }

    public void setPublication(Publication publication) {
        this.element = publication;
    }

}