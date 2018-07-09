package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.result.ClassCount;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface InteractionsRepository extends GraphRepository<Interaction> {

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE t.variantIdentifier = {0} OR (t.variantIdentifier IS NULL AND t.identifier = {0}) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, ir, re, r, pe " +
            "ORDER BY in.score DESC")
    Collection<Interaction> getByAcc(String acc);

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
            "WHERE t.variantIdentifier = {0} OR (t.variantIdentifier IS NULL AND t.identifier = {0}) " +
            "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
            "RETURN DISTINCT in, ir, re, r, pe " +
            "ORDER BY in.score DESC " +
            "SKIP ({1} - 1) * {2} " +
            "LIMIT {2}")
    Collection<Interaction> getByAcc(String acc, Integer page, Integer pageSize);

    @Query(" MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction) " +
            "WHERE t.variantIdentifier IN {0} OR (t.variantIdentifier IS NULL AND t.identifier IN {0}) " +
            "RETURN DISTINCT t.identifier AS s, COUNT(DISTINCT in) as t")
    Collection<ClassCount<String, Integer>> countByAccessions(Collection<String> accs);

    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
            "WHERE a.identifier = {0} OR a.variantIdentifier = {0} " +
            "MATCH path=(p:Pathway)-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
            "WHERE p.speciesName = {1} AND SINGLE(e IN NODES(path) WHERE (e:Pathway)) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathways(String acc, String speciesName);
}
