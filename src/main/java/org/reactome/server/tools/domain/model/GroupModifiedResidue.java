package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@SuppressWarnings("unused")
@NodeEntity
public class GroupModifiedResidue extends TranslationalModification {

    @Relationship(type = "modification", direction = Relationship.OUTGOING)
    private DatabaseObject modification;

    public GroupModifiedResidue() {}

    public DatabaseObject getModification() {
        return modification;
    }

    public void setModification(DatabaseObject modification) {
        this.modification = modification;
    }
}
