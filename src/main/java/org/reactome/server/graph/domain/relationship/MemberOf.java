package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.EntitySet;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * ComponentOf is the incoming relationship for HasComponent (SDN6) is the relationship entity of Complexes.
 * It is needed to specify the stoichiometry and order of
 * components.
 */
@SuppressWarnings("unused")
@RelationshipProperties
public class MemberOf extends Has<EntitySet> {

    @Override
    public String getType() {
        return "memberOf";
    }

    @JsonIgnore
    public EntitySet getEntitySet() {
        return element;
    }

    public void getEntitySet(EntitySet entitySet) {
        element = entitySet;
    }
}
