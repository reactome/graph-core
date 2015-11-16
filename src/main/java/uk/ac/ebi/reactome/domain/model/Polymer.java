package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.relationship.RepeatedUnit;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Polymer represents a large molecule composed of repeated subunits
 */
@NodeEntity
public class Polymer extends PhysicalEntity {

    @Relationship(type = "HAS_REPEATED_UNIT", direction = Relationship.OUTGOING)
    private RepeatedUnit repeatedUnit;

    public Polymer(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public RepeatedUnit getRepeatedUnit() {
        return repeatedUnit;
    }

    @SuppressWarnings("unused")
    public void setRepeatedUnit(RepeatedUnit repeatedUnit) {
        this.repeatedUnit = repeatedUnit;
    }
}
