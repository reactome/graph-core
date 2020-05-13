package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.PathwayResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TocRepository {

    @Query("MATCH (p:TopLevelPathway)" +
            "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit)" +
            "OPTIONAL MATCH (p)-[:reviewed]-(:InstanceEdit)" +
            "OPTIONAL MATCH (p)-[:hasEvent*]->(rle:ReactionLikeEvent)" +
            "OPTIONAL MATCH (p:TopLevelPathway{hasDiagram:True})-[:hasEvent]->(pa:Pathway)" +
            "OPTIONAL MATCH (rle)<-[:authored]-(:InstanceEdit)<-[:author]-(rlea:Person)" +
            "OPTIONAL MATCH (rle)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rler:Person)" +
            "OPTIONAL MATCH (rle)<-[:edited]-(:InstanceEdit)<-[:author]-(rlee:Person)" +
            "WITH  size(collect(distinct rlea.displayName)) AS authorsCollect, " +
            "p, " +
            "collect(distinct rlea) AS authors, " +
            "max(re.dateTime) AS revised, " +
            "collect(distinct rler) AS reviewers, " +
            "collect(distinct rlee) AS editors, " +
            "collect(distinct pa) AS subPathway " +
            "WHERE authorsCollect > 0 " +
            "RETURN p.displayName AS displayName, " +
            "p.doi AS doi, " +
            "p.releaseDate AS releaseDate, " +
            "p.releaseStatus AS releaseStatus, " +
            "p.speciesName AS species, " +
            "p.stId AS stId, " +
            "authors AS authors, " +
            "reviewers AS reviewers, " +
            "subPathway AS subPathway, " +
            "editors AS editors, " +
            "revised AS reviseDate " +
            "ORDER BY toLower(p.displayName)")
    Collection<PathwayResult> getTocPathways();
}
