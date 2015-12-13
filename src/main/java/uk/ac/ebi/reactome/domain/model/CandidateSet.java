package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * CandidateSets can additionally to DefinedSets contain candidates that are believed to be members but there is no
 * experimental data
 */
@NodeEntity
public class CandidateSet extends EntitySet {

    @Relationship(type = "hasCandidate")
    private List<PhysicalEntity> hasCandidate;

    public CandidateSet() {}

    public List<PhysicalEntity> getHasCandidate() {
        return hasCandidate;
    }

    public void setHasCandidate(List<PhysicalEntity> hasCandidate) {
        this.hasCandidate = hasCandidate;
    }
}
