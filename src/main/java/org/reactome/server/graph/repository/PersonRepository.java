package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface PersonRepository extends GraphRepository<Person>{

    @Query("MATCH (n:Person)-[r]->(m) WHERE n.surname in {0} AND n.firstname in {0} AND NOT (m:InstanceEdit) RETURN n, r, m")
    Collection<Person> findPersonByName(String[] name);

    @Query("MATCH (n:Person)-[r]->(m) WHERE n.surname in {0} OR n.firstname in {0} AND NOT (m:InstanceEdit) RETURN n, r, m")
    Collection<Person> queryPersonByName(String[] name);

    @Query("MATCH (n:Person{orcidId:{0}})-[r]->(m) WHERE n.project IS NULL AND NOT (m:InstanceEdit) RETURN n, r, m")
    Person findPersonByOrcidId(String orcidId);

    @Query("MATCH (n:Person{dbId:{0}})-[r]->(m) WHERE NOT (m:InstanceEdit) RETURN n, r, m")
    Person findPersonByDbId(Long dbId);

    @Query("MATCH (:Person{orcidId:{0}})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByOrcidId(String orcidId);

    @Query("MATCH (:Person{dbId:{0}})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByDbId(Long dbId);

    @Query("MATCH (:Person{eMailAddress:{0}})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByEmail(String email);

    @Query("MATCH (:Person{orcidId:{0}})-[:author]->(:InstanceEdit)-[:authored]->(p:Pathway) RETURN p")
    Collection<Pathway> getAuthoredPathwaysByOrcidId(String orcidId);

    @Query("MATCH (:Person{dbId:{0}})-[:author]->(:InstanceEdit)-[:authored]->(p:Pathway) RETURN p")
    Collection<Pathway> getAuthoredPathwaysByDbId(Long dbId);

    @Query("MATCH (:Person{orcidId:{0}})-[:author]->(:InstanceEdit)-[:authored]->(rle:ReactionLikeEvent) RETURN rle")
    Collection<ReactionLikeEvent> getAuthoredReactionsByOrcidId(String orcidId);

    @Query("MATCH (:Person{dbId:{0}})-[:author]->(:InstanceEdit)-[:authored]->(rle:ReactionLikeEvent) RETURN rle")
    Collection<ReactionLikeEvent> getAuthoredReactionsByDbId(Long dbId);

    @Query("MATCH (:Person{orcidId:{0}})-[:author]->(:InstanceEdit)-[:reviewed]->(p:Pathway) RETURN p")
    Collection<Pathway> getReviewedPathwaysByOrcidId(String orcidId);

    @Query("MATCH (:Person{dbId:{0}})-[:author]->(:InstanceEdit)-[:reviewed]->(p:Pathway) RETURN p")
    Collection<Pathway> getReviewedPathwaysByDbId(Long dbId);

    @Query("MATCH (:Person{orcidId:{0}})-[:author]->(:InstanceEdit)-[:reviewed]->(rle:ReactionLikeEvent) RETURN rle")
    Collection<ReactionLikeEvent> getReviewedReactionsByOrcidId(String orcidId);

    @Query("MATCH (:Person{dbId:{0}})-[:author]->(:InstanceEdit)-[:reviewed]->(rle:ReactionLikeEvent) RETURN rle")
    Collection<ReactionLikeEvent> getReviewedReactionsByDbId(Long dbId);

    @Query(" MATCH (per:Person)-[:author]->(ie:InstanceEdit) " +
            "WHERE per.project IS NULL " +
            "WITH DISTINCT per, ie " +
            "OPTIONAL MATCH (ie)-[:authored]->(ap:Pathway) " +
            "OPTIONAL MATCH (ie)-[:authored]->(ar:ReactionLikeEvent) " +
            "OPTIONAL MATCH (ie)-[:reviewed]->(rp:Pathway) " +
            "OPTIONAL MATCH (ie)-[:reviewed]->(rr:ReactionLikeEvent) " +
            "WITH DISTINCT per, SIZE(COLLECT(DISTINCT ap)) AS aps, SIZE(COLLECT(DISTINCT ar)) AS ars, SIZE(COLLECT(DISTINCT rp)) AS rps, SIZE(COLLECT(DISTINCT rr)) AS rrs " +
            "WHERE aps > 0 OR ars > 0 OR rps > 0 OR rrs > 0 " +
            "RETURN per AS person, aps AS authoredPathway, rps AS reviewedPathways, ars AS authoredReactions, rrs AS reviewedReactions")
    Collection<PersonAuthorReviewer> getAuthorsReviewers();
}
