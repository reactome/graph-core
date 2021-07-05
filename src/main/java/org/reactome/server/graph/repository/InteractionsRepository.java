package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Interaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InteractionsRepository extends Neo4jRepository<Interaction, Long> {

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE t.variantIdentifier = $acc OR (t.variantIdentifier IS NULL AND t.identifier = $acc) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, COLLECT(DISTINCT ir), COLLECT(DISTINCT re), COLLECT(DISTINCT r), COLLECT(DISTINCT pe)[..20] AS pe " +
            "ORDER BY in.score DESC")
    Collection<Interaction> getByAcc(@Param("acc") String acc);

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE t.variantIdentifier = $acc OR (t.variantIdentifier IS NULL AND t.identifier = $acc) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, COLLECT(DISTINCT ir), COLLECT(DISTINCT re), COLLECT(DISTINCT r), COLLECT(DISTINCT pe)[..20] AS pe " +
            "ORDER BY in.score DESC " +
            "SKIP ($page - 1) * $pageSize " +
            "LIMIT $pageSize")
    Collection<Interaction> getByAcc(@Param("acc") String acc, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE ( (t.variantIdentifier = $acc OR (t.variantIdentifier IS NULL AND t.identifier = $acc )) " +
            "AND (re.variantIdentifier = $accB OR (re.variantIdentifier IS NULL AND re.identifier = $accB)) ) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, COLLECT(DISTINCT ir), COLLECT(DISTINCT re), COLLECT(DISTINCT r), COLLECT(DISTINCT pe) AS pe " +
            "ORDER BY in.score DESC")
    Interaction getInteractorByAcc(@Param("acc") String acc, @Param("accB") String accB);
}
