package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * EWAS contains proteins and nucleic acids with known sequences
 */
@NodeEntity
public class EntityWithAccessionedSequence extends GenomeEncodedEntity {

    @Relationship(type = "HAS_REFERENCE_ENTITY", direction = Relationship.OUTGOING)
    private ReferenceSequence referenceSequence;

    public EntityWithAccessionedSequence() {}

    public EntityWithAccessionedSequence(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public ReferenceSequence getReferenceSequence() {
        return referenceSequence;
    }

    public void setReferenceSequence(ReferenceSequence referenceSequence) {
        this.referenceSequence = referenceSequence;
    }
}
