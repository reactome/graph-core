package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.CandidateSet;
import org.reactome.server.graph.domain.model.Complex;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * ComponentOf is the incoming relationship for HasComponent (SDN6) is the relationship entity of Complexes.
 * It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class CandidateOf extends Has<CandidateSet> {

    @Override
    public String getType() {
        return "candidateOf";
    }

    @JsonIgnore
    public CandidateSet getCandidateSet() {
        return element;
    }

    public void setCandidateSet(CandidateSet candidateSet) {
        this.element = candidateSet;
    }
}
