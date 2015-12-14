package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class GroupModifiedResidue extends TranslationalModification {

    @Relationship(type = "modification")
    private Modification modification;

    public GroupModifiedResidue() {}

    public Modification getModification() {
        return modification;
    }

    public void setModification(Modification modification) {
        this.modification = modification;
    }

//    public void setModification(DatabaseObject modification) {
//        if((modification instanceof Polymer) || (modification instanceof ReferenceGroup) || (modification instanceof EntitySet)) {
//            this.modification = modification;
//        }else{
//            throw new RuntimeException("");
//        }
//    }
}
