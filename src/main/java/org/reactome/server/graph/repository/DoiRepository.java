////package org.reactome.server.graph.repository;
////
////import org.reactome.server.graph.domain.model.DatabaseObject;
////import org.reactome.server.graph.domain.result.PathwayResult;
////import org.springframework.data.neo4j.repository.query.Query;
////import org.springframework.data.neo4j.repository.Neo4jRepository;
////import org.springframework.stereotype.Repository;
////
////import java.util.Collection;
////
////@Repository
////public interface DoiRepository extends Neo4jRepository<DatabaseObject> {
////
////    @Query("MATCH (p:Pathway)" +
////            "WHERE EXISTS(p.doi)" +
////            "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit)" +
////            "OPTIONAL MATCH (p)<-[:authored | revised]-(:InstanceEdit)<-[:author]-(patrs:Person)" +
////            "OPTIONAL MATCH (p)<-[:reviewed]-(:InstanceEdit)<-[:author]-(prvwd:Person)" +
////            "OPTIONAL MATCH (p)<-[:edited]-(:InstanceEdit)<-[:author]-(pedtd:Person)" +
////            "OPTIONAL MATCH (p)-[:hasEvent*]->(e:Event)" +
////            "OPTIONAL MATCH (e)<-[:authored]-(:InstanceEdit)<-[:author]-(rlea:Person)" +
////            "OPTIONAL MATCH (e)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rler:Person)" +
////            "OPTIONAL MATCH (e)<-[:edited]-(:InstanceEdit)<-[:author]-(rlee:Person)" +
////            "WITH collect(distinct patrs) + collect(distinct rlea) AS allAuthors, " +
////            "collect(distinct prvwd) + collect(distinct rler) AS allReviewers, " +
////            "collect(distinct pedtd) + collect(distinct rlee) AS allEditors, p, re " +
////            "UNWIND (CASE allAuthors WHEN [] then [null] else allAuthors end) AS totalAtrs " +
////            "UNWIND (CASE allReviewers WHEN [] then [null] else allReviewers end) AS totalRvwd " +
////            "UNWIND (CASE allEditors WHEN [] then [null] else allEditors end) AS totalEdtd " +
////            "RETURN p.displayName AS displayName, " +
////            "p.doi AS doi, " +
////            "p.stId AS stId, " +
////            "p.speciesName AS species, " +
////            "p.releaseDate AS releaseDate, " +
////            "p.releaseStatus AS releaseStatus, " +
////            "max(re.dateTime) AS reviseDate, " +
////            "collect(distinct totalAtrs) AS authors, " +
////            "collect(distinct totalRvwd) AS reviewers, " +
////            "collect(distinct totalEdtd) AS editors " +
////            "ORDER BY toLower(p.displayName) ")
////    Collection<PathwayResult> getDoiPathways();
////}
