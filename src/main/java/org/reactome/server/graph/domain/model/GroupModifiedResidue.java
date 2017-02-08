package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;

@SuppressWarnings("unused")
@NodeEntity
public class GroupModifiedResidue extends TranslationalModification {

    @Relationship(type = "modification", direction = Relationship.OUTGOING)
    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    private DatabaseObject modification;

    public GroupModifiedResidue() {}

    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    public DatabaseObject getModification() {
        return modification;
    }

    @Relationship(type = "modification", direction = Relationship.OUTGOING)
    public void setModification(DatabaseObject modification) {
        this.modification = modification;
    }
}
