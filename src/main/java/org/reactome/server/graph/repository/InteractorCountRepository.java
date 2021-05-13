package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.InteractorsCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class InteractorCountRepository {

    private final Neo4jClient neo4jClient;

    @Autowired
    public InteractorCountRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<InteractorsCount> countByAccessions(Collection<String> accs) {
        String query = " " +
                "MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction) " +
                "WHERE t.variantIdentifier IN $accs OR (t.variantIdentifier IS NULL AND t.identifier IN $accs) " +
                "RETURN DISTINCT t.identifier AS s, COUNT(DISTINCT in) as t";
        return neo4jClient.query(query).bindAll(Collections.singletonMap("accs", accs)).fetchAs(InteractorsCount.class).mappedBy( (t, s) -> InteractorsCount.build(s)).all();
    }

}
