package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.result.ClassCount;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
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
            "WHERE SINGLE(e IN NODES(path) WHERE (e:Pathway)) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathways(String acc);

    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
            "WHERE a.identifier = {0} OR a.variantIdentifier = {0} " +
            "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
            "WHERE p.speciesName = {1} AND SINGLE(e IN NODES(path) WHERE (e:Pathway) AND e.hasDiagram) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getDiagrammedLowerLevelPathways(String acc, String speciesName);

    @Query(" MATCH (re:ReferenceEntity)<-[:interactor]-()-[:interactor]->()<-[:referenceEntity]-(pe:PhysicalEntity) " +
            "WHERE re.identifier = {0} OR re.variantIdentifier = {0} " +
            "WITH DISTINCT pe " +
            "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH p, COLLECT(DISTINCT pe) AS entities " +
            "OPTIONAL MATCH (d:Pathway{hasDiagram:True})-[:hasEvent*]->(p)  " +
            "WITH p, entities, p + COLLECT(DISTINCT d) AS all " +
            "UNWIND all AS d " +
            "OPTIONAL MATCH p1=(d)-[:hasEvent*]->(sp:Pathway{hasDiagram:True}) " +
            "WHERE sp IN all AND SINGLE(x IN TAIL(NODES(p1)) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN DISTINCT p AS diagram, false AS inDiagram, CASE WHEN d=p THEN entities ELSE COLLECT(DISTINCT sp) END AS occurrences")
    Collection<DiagramOccurrences> getDiagramOccurrences(String identifier);
}
