package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.PathwayResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TocRepository extends GraphRepository<DatabaseObject> {

    @Query("MATCH (p:TopLevelPathway{isInferred:false}) " +
            "MATCH (p)-[:hasEvent]->(subs:Pathway) " +
            "MATCH (p)-[:hasEvent*]->(otherp:Event) " +
            "OPTIONAL MATCH (subs)<-[:revised]-(re:InstanceEdit) " +
            "OPTIONAL MATCH (otherp)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(atrs:Person) " +
            "OPTIONAL MATCH (otherp)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rvwd:Person) " +
            "OPTIONAL MATCH (otherp)<-[:edited]-(:InstanceEdit)<-[:author]-(edtd:Person) " +
            "OPTIONAL MATCH (p)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(tatrs:Person) " +
            "OPTIONAL MATCH (p)<-[:reviewed]-(:InstanceEdit)<-[:author]-(trvwd:Person) " +
            "OPTIONAL MATCH (p)<-[:edited]-(:InstanceEdit)<-[:author]-(tedtd:Person) " +
            "WITH collect(distinct tatrs) + collect(distinct atrs) AS allAuthors,  " +
            "collect(distinct trvwd) + collect(distinct rvwd) AS allReviewers,  " +
            "collect(distinct tedtd) + collect(distinct edtd) AS allEditors,  " +
            "p, re, subs  " +
            "UNWIND allAuthors AS totalAtrs " +
            "UNWIND allReviewers AS totalRvwd " +
            "UNWIND allEditors AS totalEdtd " +
            "RETURN p.stId AS stId, p.displayName AS displayName, p.doi AS doi, p.speciesName AS species, " +
            "p.releaseDate AS releaseDate,  " +
            "max(re.dateTime) AS reviseDate,  " +
            "collect(distinct totalRvwd) AS reviewers,  " +
            "collect(distinct totalEdtd) AS editors,  " +
            "collect(distinct totalAtrs) AS authors,  " +
            "p.releaseStatus AS releaseStatus,  " +
            "collect(distinct subs) AS subPathway  " +
            "ORDER BY toLower(p.displayName)")
    Collection<PathwayResult> getTocPathways();
}
