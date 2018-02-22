package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface DiagramRepository extends GraphRepository<PhysicalEntity> {

    @Query(" MATCH (e:Pathway{dbId:{0}, hasDiagram:True}) " +
            "RETURN e.stId As diagramStId, [] AS events, e.diagramWidth AS width, e.diagramHeight AS height  " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(:Pathway{dbId:{0}, hasDiagram:False})-[:hasEvent*]->(r:ReactionLikeEvent) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, COLLECT(DISTINCT r.stId) AS events, d.diagramWidth AS width, d.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{dbId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, [{0}] AS events, d.diagramWidth AS width, d.diagramHeight AS height")
    DiagramResult getDiagramResult(Long dbId);

    @Query(" MATCH (e:Pathway{stId:{0}, hasDiagram:True}) " +
            "RETURN e.stId As diagramStId, [] AS events, e.diagramWidth AS width, e.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(:Pathway{stId:{0}, hasDiagram:False})-[:hasEvent*]->(r:ReactionLikeEvent) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, COLLECT(DISTINCT r.stId) AS events, d.diagramWidth AS width, d.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{stId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, [{0}] AS events, d.diagramWidth AS width, d.diagramHeight AS height")
    DiagramResult getDiagramResult(String stId);

    @Query(" MATCH path=allShortestPaths((p:Pathway)-[:hasEvent|hasEncapsulatedEvent*]->(:Event{dbId:{0}})) " +
            "WITH FILTER(p IN NODES(path) WHERE (p:Pathway) AND NOT p.hasDiagram IS NULL AND p.hasDiagram) AS pathways " +
            "WHERE SIZE(pathways) > 0 " +
            "WITH DISTINCT HEAD(pathways) as p, HEAD(TAIL(pathways)) as s " +
            "RETURN p as pathway, s as subpathway " +
            "ORDER BY p.stId " +
            "UNION " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{dbId:{0}}) " +
            "WITH DISTINCT rle " +
            "MATCH path=allShortestPaths((p:Pathway)-[:hasEvent|hasEncapsulatedEvent*]->(rle)) " +
            "WITH FILTER(p IN NODES(path) WHERE (p:Pathway) AND NOT p.hasDiagram IS NULL AND p.hasDiagram) AS pathways " +
            "WHERE SIZE(pathways) > 0 " +
            "WITH DISTINCT HEAD(pathways) as p, HEAD(TAIL(pathways)) as s " +
            "RETURN p as pathway, s as subpathway " +
            "ORDER BY p.stId")
    Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId);

    @Query(" MATCH path=allShortestPaths((p:Pathway)-[:hasEvent|hasEncapsulatedEvent*]->(:Event{stId:{0}})) " +
            "WITH FILTER(p IN NODES(path) WHERE (p:Pathway) AND NOT p.hasDiagram IS NULL AND p.hasDiagram) AS pathways " +
            "WHERE SIZE(pathways) > 0 " +
            "WITH DISTINCT HEAD(pathways) as p, HEAD(TAIL(pathways)) as s " +
            "RETURN p as pathway, s as subpathway " +
            "ORDER BY p.stId " +
            "UNION " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{stId:{0}}) " +
            "WITH DISTINCT rle " +
            "MATCH path=allShortestPaths((p:Pathway)-[:hasEvent|hasEncapsulatedEvent*]->(rle)) " +
            "WITH FILTER(p IN NODES(path) WHERE (p:Pathway) AND NOT p.hasDiagram IS NULL AND p.hasDiagram) AS pathways " +
            "WHERE SIZE(pathways) > 0 " +
            "WITH DISTINCT HEAD(pathways) as p, HEAD(TAIL(pathways)) as s " +
            "RETURN p as pathway, s as subpathway " +
            "ORDER BY p.stId")
    Collection<DiagramOccurrences> getDiagramOccurrences(String stId);
}
