package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@SuppressWarnings("unused")
@Node
public class GroupModifiedResidue extends TranslationalModification {

    @Relationship(type = "modification")
    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    private DatabaseObject modification;

    public GroupModifiedResidue() {}

    @ReactomeAllowedClasses(allowed = {EntitySet.class, Polymer.class, ReferenceGroup.class})
    public DatabaseObject getModification() {
        return modification;
    }

    public void setModification(DatabaseObject modification) {
        this.modification = modification;
    }
}
