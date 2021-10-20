package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Publication;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PublicationRepository extends Neo4jRepository<Publication, Long> {

    @Query("MATCH (:Person{orcidId:$orcidId})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByOrcidId(@Param("orcidId") String orcidId);

    @Query("MATCH (:Person{dbId:$dbId})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByDbId(@Param("dbId") Long dbId);

    @Query("MATCH (:Person{eMailAddress:$email})-[:author]-(pub:Publication) RETURN pub")
    Collection<Publication> getPublicationsOfPersonByEmail(@Param("email") String email);
}
