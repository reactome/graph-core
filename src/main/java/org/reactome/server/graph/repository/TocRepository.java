//package org.reactome.server.graph.repository;
//
//import org.reactome.server.graph.domain.model.DatabaseObject;
//import org.reactome.server.graph.domain.result.PathwayResult;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//
//@Repository
//public interface TocRepository extends CrudRepository {
//
//    @Query("MATCH (p:TopLevelPathway{isInferred:false}) " +
//            "MATCH (p)-[:hasEvent]->(subs:Pathway) " +
//            "WITH  COLLECT(distinct subs) AS subPathway, p " +
//            "MATCH (p)-[:hasEvent*]->(otherp:Event) " +
//            "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit) " +
//            "OPTIONAL MATCH (otherp)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(atrs:Person) " +
//            "OPTIONAL MATCH (otherp)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rvwd:Person) " +
//            "OPTIONAL MATCH (otherp)<-[:edited]-(:InstanceEdit)<-[:author]-(edtd:Person) " +
//            "OPTIONAL MATCH (p)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(tatrs:Person) " +
//            "OPTIONAL MATCH (p)<-[:reviewed]-(:InstanceEdit)<-[:author]-(trvwd:Person) " +
//            "OPTIONAL MATCH (p)<-[:edited]-(:InstanceEdit)<-[:author]-(tedtd:Person) " +
//            "WITH collect(distinct tatrs) + collect(distinct atrs) AS allAuthors,  " +
//            "collect(distinct trvwd) + collect(distinct rvwd) AS allReviewers,  " +
//            "collect(distinct tedtd) + collect(distinct edtd) AS allEditors,  " +
//            "p, re, subPathway  " +
//            "UNWIND allAuthors AS totalAtrs " +
//            "UNWIND allReviewers AS totalRvwd " +
//            "UNWIND allEditors AS totalEdtd " +
//            "RETURN p.stId AS stId, p.displayName AS displayName, p.doi AS doi, p.speciesName AS species, " +
//            "p.releaseDate AS releaseDate,  " +
//            "max(re.dateTime) AS reviseDate,  " +
//            "collect(distinct totalRvwd) AS reviewers,  " +
//            "collect(distinct totalEdtd) AS editors,  " +
//            "collect(distinct totalAtrs) AS authors,  " +
//            "p.releaseStatus AS releaseStatus,  " +
//            "subPathway  " +
//            "ORDER BY toLower(p.displayName)")
//    Collection<PathwayResult> getTocPathways();
//}
