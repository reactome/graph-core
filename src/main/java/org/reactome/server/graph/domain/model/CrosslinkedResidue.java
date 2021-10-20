package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public abstract class CrosslinkedResidue extends TranslationalModification {

    @ReactomeProperty
    private Integer secondCoordinate;

    @Relationship(type = "modification")
    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    private DatabaseObject modification;

    public CrosslinkedResidue() {}

    public Integer getSecondCoordinate() {
        return secondCoordinate;
    }

    public void setSecondCoordinate(Integer secondCoordinate) {
        this.secondCoordinate = secondCoordinate;
    }

    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    public DatabaseObject getModification() {
        return modification;
    }

    public void setModification(DatabaseObject modification) {
        if(modification == null) return;


        if (modification instanceof Polymer || modification instanceof ReferenceGroup || modification instanceof EntitySet) {
            this.modification = modification;
        } else {
            throw new RuntimeException(modification + " is not a Polymer, ReferenceGroup or EntitySet");
        }
    }
}
