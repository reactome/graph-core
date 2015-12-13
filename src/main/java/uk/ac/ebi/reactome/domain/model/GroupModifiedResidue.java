package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class GroupModifiedResidue extends TranslationalModification {

    @Relationship
    private DatabaseObject modification;

    public GroupModifiedResidue() {

    }

    public DatabaseObject getModification() {
        return modification;
    }

    public void setModification(DatabaseObject modification) {
        if((modification instanceof Polymer) || (modification instanceof ReferenceGroup) || (modification instanceof EntitySet)) {
            this.modification = modification;
        }else{
            throw new RuntimeException("");
        }
    }

}
