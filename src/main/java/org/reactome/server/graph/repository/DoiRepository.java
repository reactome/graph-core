package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.PathwayResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DoiRepository {

    @Query("MATCH (p:Pathway)" +
            "WHERE EXISTS(p.doi)" +
            "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit)" +
            "OPTIONAL MATCH (p)-[:reviewed]-(:InstanceEdit)" +
            "OPTIONAL MATCH (p)-[:hasEvent*]->(rle:ReactionLikeEvent)" +
            "OPTIONAL MATCH (rle)<-[:authored]-(:InstanceEdit)<-[:author]-(rlea:Person)" +
            "OPTIONAL MATCH (rle)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rler:Person)" +
            "OPTIONAL MATCH (rle)<-[:edited]-(:InstanceEdit)<-[:author]-(rlee:Person)" +
            "RETURN p.displayName AS displayName, " +
            "p.doi AS doi, " +
            "p.stId AS stId, " +
            "p.speciesName AS species, " +
            "p.releaseDate AS releaseDate, " +
            "p.releaseStatus AS releaseStatus, " +
            "max(re.dateTime) AS reviseDate, " +
            "collect(distinct rlea) AS authors, " +
            "collect(distinct rler) AS reviewers, " +
            "collect(distinct rlee) AS editors " +
            "ORDER BY toLower(p.displayName)")
    Collection<PathwayResult> getDoiPathways();
}
