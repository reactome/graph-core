package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * OpenSet are used when the limit of the Set cannot be defined (not all members can be listed)
 */
@NodeEntity
public class OpenSet extends EntitySet {

    @Relationship(type = "HAS_REFERENCE_ENTITY", direction = Relationship.OUTGOING)
    private ReferenceGroup referenceGroup;

    public OpenSet(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public ReferenceGroup getReferenceGroup() {
        return referenceGroup;
    }

    public void setReferenceGroup(ReferenceGroup referenceGroup) {
        this.referenceGroup = referenceGroup;
    }
}
