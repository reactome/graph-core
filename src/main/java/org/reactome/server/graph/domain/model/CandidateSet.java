package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

import java.util.List;

/**
 * A set of entities that are interchangeable in function, with two subclasses, members that are hypothetical and members that have been demonstrated. Hypothetical members are identified as values of the hasCandidate slot. Members that have been demonstrated are identified in the hasMember slot. At least one hasCandidate value is required; hasMember values are optional.
 */
@SuppressWarnings("unused")
@NodeEntity
public class CandidateSet extends EntitySet {

    @Relationship(type = "hasCandidate")
    private List<PhysicalEntity> hasCandidate;

    public CandidateSet() {}

    public List<PhysicalEntity> getHasCandidate() {
        return hasCandidate;
    }

    @Relationship(type = "hasCandidate")
    public void setHasCandidate(List<PhysicalEntity> hasCandidate) {
        this.hasCandidate = hasCandidate;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        return "A set of entities that are interchangeable in function, with two subclasses, members that are hypothetical and members that have been demonstrated. " +
                "Hypothetical members are identified as values of the hasCandidate slot. Members that have been demonstrated are identified in the hasMember slot. " +
                "At least one hasCandidate value is required; hasMember values are optional";

    }
}
