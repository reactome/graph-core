package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.Referrals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class ReferralsLinkageRepository {

    private final Neo4jClient neo4jClient;
    Neo4jTemplate n;

    @Autowired
    public ReferralsLinkageRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<Referrals> getReferralsTo(String stId) {
        String query = " " +
                "MATCH (d:DatabaseObject{stId:$stId})<-[rel]-(ref) " +
                "WHERE NOT ref:InstanceEdit " +
                "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
                "RETURN DISTINCT TYPE(rel) AS referral, " +
                "       COLLECT(ref) AS objects " +
                "LIMIT 1000";
        return neo4jClient.query(query).bindAll(Collections.singletonMap("stId", stId)).fetchAs(Referrals.class).mappedBy( (t, record) -> Referrals.build(record)).all();

    }

    public Collection<Referrals> getReferralsTo(Long dbId) {
        String query = " " +
                " MATCH (d:DatabaseObject{dbId:$dbId})<-[rel]-(ref) " +
                "WHERE NOT ref:InstanceEdit " +
                "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
                "RETURN DISTINCT TYPE(rel) AS referral, " +
                "       COLLECT(ref) AS objects " +
                "LIMIT 1000";
        return neo4jClient.query(query).bindAll(Collections.singletonMap("dbId", dbId)).fetchAs(Referrals.class).mappedBy( (t, record) -> Referrals.build(record)).all();

    }
}
