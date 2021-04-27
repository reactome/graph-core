package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends Neo4jRepository<Event, Long> {

    @Query("MATCH (p:Pathway)-[r:hasEvent*]->(e:Event) WHERE p.stId = $stId RETURN e, COLLECT(r), COLLECT(p)")
    Collection<Event> getContainedEventsByStId(@Param("stId") String stId);

    @Query("MATCH (p:Pathway)-[r:hasEvent*]->(e:Event) WHERE p.dbId = $dbId RETURN e, COLLECT(r), COLLECT(p)")
    Collection<Event> getContainedEventsByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:Person{orcidId:$orcidId})-[:author]->(ie:InstanceEdit)-[:authored]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT ie, rle ORDER BY ie.dateTime DESC " +
            "RETURN rle")
    Collection<? extends Event> getAuthoredReactionsByOrcidId(@Param("orcidId") String orcidId);

    @Query(" MATCH (:Person{dbId:$dbId})-[:author]->(ie:InstanceEdit)-[:authored]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT ie, rle ORDER BY ie.dateTime DESC " +
            "RETURN rle")
    Collection<? extends Event> getAuthoredReactionsByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:Person{orcidId:$orcidId})-[:author]->(ie:InstanceEdit)-[:reviewed]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT ie, rle ORDER BY ie.dateTime DESC " +
            "RETURN rle")
    Collection<? extends Event> getReviewedReactionsByOrcidId(@Param("orcidId") String orcidId);

    @Query(" MATCH (:Person{dbId:$dbId})-[:author]->(ie:InstanceEdit)-[:reviewed]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT ie, rle ORDER BY ie.dateTime DESC " +
            "RETURN rle")
    Collection<? extends Event> getReviewedReactionsByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:Person{orcidId:$orcidId})-[:author]->(ie:InstanceEdit)-[:authored]->(p:Pathway) " +
            "WITH DISTINCT ie, p ORDER BY ie.dateTime DESC " +
            "RETURN p")
    Collection<? extends Event> getAuthoredPathwaysByOrcidId(@Param("orcidId") String orcidId);

    @Query(" MATCH (:Person{dbId:$dbId})-[:author]->(ie:InstanceEdit)-[:authored]->(p:Pathway) " +
            "WITH DISTINCT ie, p ORDER BY ie.dateTime DESC " +
            "RETURN p")
    Collection<? extends Event> getAuthoredPathwaysByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:Person{orcidId:$orcidId})-[:author]->(ie:InstanceEdit)-[:reviewed]->(p:Pathway) " +
            "WITH DISTINCT ie, p ORDER BY ie.dateTime DESC " +
            "RETURN p")
    Collection<? extends Event> getReviewedPathwaysByOrcidId(@Param("orcidId") String orcidId);

    @Query(" MATCH (:Person{dbId:$dbId})-[:author]->(ie:InstanceEdit)-[:reviewed]->(p:Pathway) " +
            "WITH DISTINCT ie, p ORDER BY ie.dateTime DESC " +
            "RETURN p")
    Collection<? extends Event> getReviewedPathwaysByDbId(@Param("dbId") Long dbId);

}
