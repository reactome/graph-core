package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.domain.relationship.HasCandidate;
import org.reactome.server.graph.domain.relationship.CompositionAggregator;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;
import java.util.stream.Stream;

/**
 * A set of entities that are interchangeable in function, with two subclasses, members that are hypothetical and members that have been demonstrated. Hypothetical members are identified as values of the hasCandidate slot. Members that have been demonstrated are identified in the hasMember slot. At least one hasCandidate value is required; hasMember values are optional.
 */
@SuppressWarnings("unused")
@Node
public class CandidateSet extends EntitySet implements CompositionAggregator {

    @Relationship(type = Relationships.HAS_CANDIDATE)
    private SortedSet<HasCandidate> hasCandidate;

    @Override
    public Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations() {
        return Stream.concat(super.defineCompositionRelations(), Stream.of(hasCandidate));
    }

    public CandidateSet() {
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public SortedSet<HasCandidate> getCandidates() {
        return hasCandidate;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setCandidates(SortedSet<HasCandidate> hasCandidate) {
        this.hasCandidate = hasCandidate;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<PhysicalEntity> getHasCandidate() {
        return Has.Util.expandStoichiometry(this.hasCandidate);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setHasCandidate(List<PhysicalEntity> hasCandidate) {
        this.hasCandidate = Has.Util.aggregateStoichiometry(hasCandidate, HasCandidate::new);
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
