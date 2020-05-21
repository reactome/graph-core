package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;

@NodeEntity
public class ModifiedNucleotide extends TranscriptionalModification {

    @ReactomeProperty
    private Integer coordinate;

    @Relationship(type = "modification")
    private DatabaseObject modification;

    public ModifiedNucleotide() {
    }

    public Integer getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer coordinate) {
        this.coordinate = coordinate;
    }

    public DatabaseObject getModification() {
        return modification;
    }

    @Relationship(type = "modification")
    public void setModification(DatabaseObject modification) {
        this.modification = modification;
    }

}