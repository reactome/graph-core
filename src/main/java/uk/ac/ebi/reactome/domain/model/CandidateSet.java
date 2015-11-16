package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * CandidateSets can additionally to DefinedSets contain candidates that are believed to be members but there is no
 * experimental data
 */
@NodeEntity
public class CandidateSet extends EntitySet {

    @Relationship(type = "HAS_CANDIDATE", direction = Relationship.OUTGOING)
    private Set<PhysicalEntity> candidates;

    public CandidateSet() {}

    public CandidateSet(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }

    public Set<PhysicalEntity> getCandidates() {
        return candidates;
    }

    @SuppressWarnings("unused")
    public void setCandidates(Set<PhysicalEntity> candidates) {
        this.candidates = candidates;
    }
}
