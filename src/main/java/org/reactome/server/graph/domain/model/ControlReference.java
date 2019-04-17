package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class ControlReference extends DatabaseObject {

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    public ControlReference() {
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    @Relationship(type = "literatureReference")
    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }
}
