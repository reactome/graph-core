package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.relationship.HasCandidate;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

/**
 * A set of entities that are interchangeable in function, with two subclasses, members that are hypothetical and members that have been demonstrated. Hypothetical members are identified as values of the hasCandidate slot. Members that have been demonstrated are identified in the hasMember slot. At least one hasCandidate value is required; hasMember values are optional.
 */
@SuppressWarnings("unused")
@Node
public class CandidateSet extends EntitySet {

    @Relationship(type = "hasCandidate")
    private SortedSet<HasCandidate> hasCandidate;

    public CandidateSet() {}

    public List<PhysicalEntity> getHasCandidate() {
        List<PhysicalEntity> rtn = null;
        if (hasCandidate != null) {
            rtn = new ArrayList<>();
            //stoichiometry does NOT need to be taken into account here
            for (HasCandidate candidate : hasCandidate) {
                rtn.add(candidate.getPhysicalEntity());
            }
        }
        return rtn;
    }

    public void setHasCandidate(List<PhysicalEntity> hasCandidate) {
        if (hasCandidate == null) return;
        Map<Long, HasCandidate> components = new LinkedHashMap<>();
        int order = 0;
        for (PhysicalEntity physicalEntity : hasCandidate) {
            //stoichiometry does NOT need to be taken into account here
            HasCandidate aux = new HasCandidate();
//            aux.setEntitySet(this);
            aux.setPhysicalEntity(physicalEntity);
            aux.setOrder(order++);
            components.put(physicalEntity.getDbId(), aux);
        }
        this.hasCandidate = new TreeSet<>(components.values());
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
