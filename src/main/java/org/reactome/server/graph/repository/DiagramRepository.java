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
            "WITH COLLECT(DISTINCT p) AS diagrammedPathways " +
            "UNWIND diagrammedPathways AS d " +
            "MATCH path=(d)-[:hasEvent*]->(e) " +
            "WHERE SINGLE(p IN NODES(path) WHERE (p:Pathway) AND p.hasDiagram) " +
            "WITH diagrammedPathways, COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND diagrammedPathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, diagrammedPathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN directlyInDiagram " +
            "OPTIONAL MATCH (p)-[:hasEvent]->(sp:Pathway) " +
            "WHERE sp IN all " +
            "RETURN DISTINCT p as diagram, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS subpathways " +
            "UNION " +
            "MATCH (e:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{dbId:{0}}) " +
            "WITH DISTINCT e " +
            "MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e) " +
            "WITH COLLECT(DISTINCT p) AS diagrammedPathways " +
            "UNWIND diagrammedPathways AS d " +
            "MATCH path=(d)-[:hasEvent*]->(e) " +
            "WHERE SINGLE(p IN NODES(path) WHERE (p:Pathway) AND p.hasDiagram) " +
            "WITH diagrammedPathways, COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND diagrammedPathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, diagrammedPathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN directlyInDiagram " +
            "OPTIONAL MATCH (p)-[:hasEvent]->(sp:Pathway) " +
            "WHERE sp IN all " +
            "RETURN DISTINCT p as diagram, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS subpathways")
    Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId);

    @Query(" MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e:Event{stId:{0}}) " +
            "WITH COLLECT(DISTINCT p) AS diagrammedPathways " +
            "UNWIND diagrammedPathways AS d " +
            "MATCH path=(d)-[:hasEvent*]->(e) " +
            "WHERE SINGLE(p IN NODES(path) WHERE (p:Pathway) AND p.hasDiagram) " +
            "WITH diagrammedPathways, COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND diagrammedPathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, diagrammedPathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN directlyInDiagram " +
            "OPTIONAL MATCH (p)-[:hasEvent]->(sp:Pathway) " +
            "WHERE sp IN all " +
            "RETURN DISTINCT p as diagram, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS subpathways " +
            "UNION " +
            "MATCH (e:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(t:DatabaseObject{stId:{0}}) " +
            "WITH DISTINCT e " +
            "MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(e) " +
            "WITH COLLECT(DISTINCT p) AS diagrammedPathways " +
            "UNWIND diagrammedPathways AS d " +
            "MATCH path=(d)-[:hasEvent*]->(e) " +
            "WHERE SINGLE(p IN NODES(path) WHERE (p:Pathway) AND p.hasDiagram) " +
            "WITH diagrammedPathways, COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND diagrammedPathways as t " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent*..3]->(t) " +
            "WITH directlyInDiagram, diagrammedPathways + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all as p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN directlyInDiagram " +
            "OPTIONAL MATCH (p)-[:hasEvent]->(sp:Pathway) " +
            "WHERE sp IN all " +
            "RETURN DISTINCT p as diagram, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS subpathways")
    Collection<DiagramOccurrences> getDiagramOccurrences(String stId);
}
