package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CustomDTOsRepository {

    @Autowired
    private Neo4jClient neo4jClient;

    public Collection<PersonAuthorReviewer> getAuthorsReviewers() {
        String query = " " +
                "MATCH (per:Person)-[:author]->(ie:InstanceEdit) " +
                "WITH DISTINCT per, ie " +
                "OPTIONAL MATCH (ie)-[:authored]->(ap:Pathway) " +
                "OPTIONAL MATCH (ie)-[:authored]->(ar:ReactionLikeEvent) " +
                "OPTIONAL MATCH (ie)-[:reviewed]->(rp:Pathway) " +
                "OPTIONAL MATCH (ie)-[:reviewed]->(rr:ReactionLikeEvent) " +
                "WITH DISTINCT per, SIZE(COLLECT(DISTINCT ap)) AS aps, SIZE(COLLECT(DISTINCT ar)) AS ars, SIZE(COLLECT(DISTINCT rp)) AS rps, SIZE(COLLECT(DISTINCT rr)) AS rrs " +
                "WHERE aps > 0 OR ars > 0 OR rps > 0 OR rrs > 0 " +
                "RETURN per AS person, aps AS authoredPathways, rps AS reviewedPathways, ars AS authoredReactions, rrs AS reviewedReactions";

        return neo4jClient.query(query).fetchAs(PersonAuthorReviewer.class).mappedBy(((typeSystem, record) -> PersonAuthorReviewer.build(record))).all();
    }
}
