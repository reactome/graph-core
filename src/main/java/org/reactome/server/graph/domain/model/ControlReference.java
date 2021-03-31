package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@Node
public abstract class ControlReference extends DatabaseObject {

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    public ControlReference() {
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }
}
