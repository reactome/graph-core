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

    @Query(" MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e:Event{dbId:{0}}) " +
            "WITH COLLECT(DISTINCT p) AS pathways " +
            "UNWIND pathways AS aux " +
            "MATCH path=(aux)-[:hasEvent*]->(rle) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH pathways, COLLECT(DISTINCT aux) AS directlyInDiagram " +
            "UNWIND pathways as t " +
            "MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WHERE NOT (t)-[:hasEncapsulatedEvent*..3]->(cep) " +
            "WITH directlyInDiagram, pathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEvent|hasEncapsulatedEvent]->(ecc:Pathway) " +
            "WHERE ecc IN all AND (NOT p IN directlyInDiagram OR NOT (ecc)-[:hasEncapsulatedEvent]->(p)) " +
            "RETURN DISTINCT p as pathway, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ecc) AS subpathways " +
            "ORDER BY p.dbId " +
            "UNION " +
            "MATCH (e:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{dbId:{0}}) " +
            "WITH DISTINCT e " +
            "MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e) " +
            "WITH COLLECT(DISTINCT p) AS pathways " +
            "UNWIND pathways AS aux " +
            "MATCH path=(aux)-[:hasEvent*]->(rle) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH pathways, COLLECT(DISTINCT aux) AS directlyInDiagram " +
            "UNWIND pathways as t " +
            "MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WHERE NOT (t)-[:hasEncapsulatedEvent*..3]->(cep) " +
            "WITH directlyInDiagram, pathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEvent|hasEncapsulatedEvent]->(ecc:Pathway) " +
            "WHERE ecc IN all AND (NOT p IN directlyInDiagram OR NOT (ecc)-[:hasEncapsulatedEvent]->(p)) " +
            "RETURN DISTINCT p as pathway, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ecc) AS subpathways " +
            "ORDER BY p.dbId")
    Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId);

    @Query(" MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e:Event{stId:{0}}) " +
            "WITH COLLECT(DISTINCT p) AS pathways " +
            "UNWIND pathways AS aux " +
            "MATCH path=(aux)-[:hasEvent*]->(rle) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH pathways, COLLECT(DISTINCT aux) AS directlyInDiagram " +
            "UNWIND pathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, pathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEvent|hasEncapsulatedEvent]->(ecc:Pathway) " +
            "WHERE ecc IN all AND (NOT p IN directlyInDiagram OR NOT (ecc)-[:hasEncapsulatedEvent]->(p)) " +
            "RETURN DISTINCT p as pathway, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ecc) AS subpathways " +
            "ORDER BY p.dbId " +
            "UNION " +
            "MATCH (e:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{stId:{0}}) " +
            "WITH DISTINCT e " +
            "MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e) " +
            "WITH COLLECT(DISTINCT p) AS pathways " +
            "UNWIND pathways AS aux " +
            "MATCH path=(aux)-[:hasEvent*]->(rle) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH pathways, COLLECT(DISTINCT aux) AS directlyInDiagram " +
            "UNWIND pathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, pathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEvent|hasEncapsulatedEvent]->(ecc:Pathway) " +
            "WHERE ecc IN all AND (NOT p IN directlyInDiagram OR NOT (ecc)-[:hasEncapsulatedEvent]->(p)) " +
            "RETURN DISTINCT p as pathway, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ecc) AS subpathways " +
            "ORDER BY p.dbId")
    Collection<DiagramOccurrences> getDiagramOccurrences(String stId);
}
