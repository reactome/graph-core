package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CrosslinkedResidue extends TranslationalModification {

    @Relationship
    private DatabaseObject modification;

    private Integer secondCoordinate;

    public CrosslinkedResidue() {

    }

    public DatabaseObject getModification() {
        return modification;
    }

    public void setModification(DatabaseObject modification) {
        if ((modification instanceof Polymer) || (modification instanceof ReferenceGroup) || (modification instanceof EntitySet)) {
            this.modification = modification;
        } else {
            throw new RuntimeException(modification + " is not a Polymer, ReferenceGroup or EntitySet");
        }
    }

    public Integer getSecondCoordinate() {
        return secondCoordinate;
    }

    public void setSecondCoordinate(Integer secondCoordinate) {
        this.secondCoordinate = secondCoordinate;
    }

}
