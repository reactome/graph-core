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
            "RETURN DISTINCT in, collect(ir), collect(re), collect(r), collect(pe) " +
            "ORDER BY in.score DESC")
    Collection<Interaction> getByAcc(@Param("acc") String acc);

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE t.variantIdentifier = $acc OR (t.variantIdentifier IS NULL AND t.identifier = $acc) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, collect(ir), collect(re), collect(r), collect(pe) " +
            "ORDER BY in.score DESC " +
            "SKIP ($page - 1) * $pageSize " +
            "LIMIT $pageSize")
    Collection<Interaction> getByAcc(@Param("acc") String acc, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

}
