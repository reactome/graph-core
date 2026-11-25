package org.reactome.server.graph.domain.model;

import java.util.List;
import java.util.SortedSet;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.PublicationAuthor;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public abstract class Publication extends DatabaseObject {
    private static final long serialVersionUID = 1L;

    @ReactomeProperty
    private String title;

    @Relationship(type = "author", direction = Relationship.Direction.INCOMING)
    private SortedSet<PublicationAuthor> author;

    public Publication() {}

    public Publication(Long dbId) {
        super(dbId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Person> getAuthor() {
        return Has.Util.expandStoichiometry(author);
    }

    public void setAuthor(List<Person> author) {
        this.author = Has.Util.aggregateStoichiometry(author, PublicationAuthor::new);
    }
}
