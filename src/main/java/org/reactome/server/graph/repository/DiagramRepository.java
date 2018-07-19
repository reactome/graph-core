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

    @Query(" MATCH (d:Pathway{dbId:{0}, hasDiagram:True}) " +
            "RETURN d.stId As diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(s:Pathway{dbId:{0}, hasDiagram:False}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH DISTINCT d, s " +
            "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
            "WHERE NOT (d:TopLevelPathway) " +
            "RETURN d.stId as diagramStId, [s.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
            "ORDER BY level LIMIT 1 " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{dbId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH DISTINCT d, r " +
            "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
            "WHERE NOT (d:TopLevelPathway) " +
            "RETURN d.stId as diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
            "ORDER BY level LIMIT 1")
    DiagramResult getDiagramResult(Long dbId);

    @Query(" MATCH (d:Pathway{stId:{0}, hasDiagram:True}) " +
            "RETURN d.stId As diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(s:Pathway{stId:{0}, hasDiagram:False}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH DISTINCT d, s " +
            "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
            "WHERE NOT (d:TopLevelPathway) " +
            "RETURN d.stId as diagramStId, [s.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
            "ORDER BY level LIMIT 1 " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{stId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "WITH DISTINCT d, r " +
            "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
            "WHERE NOT (d:TopLevelPathway) " +
            "RETURN d.stId as diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
            "ORDER BY level LIMIT 1")
    DiagramResult getDiagramResult(String stId);

    @Query(" MATCH (i:DatabaseObject{dbId:{0}}) " +
            "OPTIONAL MATCH (i)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
            "WHERE re.trivial IS NULL " +
            "WITH COLLECT(DISTINCT pe) AS interactors, i + COLLECT(DISTINCT pe) AS objs " +
            "UNWIND objs as obj " +
            "OPTIONAL MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(obj) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "OPTIONAL MATCH (d:Pathway{hasDiagram:True, dbId:{0}}) " +
            "WITH interactors, COLLECT(DISTINCT p) + COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND directlyInDiagram AS d " +
            "OPTIONAL MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(d) " +
            "WITH interactors, directlyInDiagram, directlyInDiagram + COLLECT(DISTINCT p) AS hlds " +
            "UNWIND hlds AS d " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
            "WITH interactors, directlyInDiagram, hlds + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all AS p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN all " +
            "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
            "WHERE sp IN all AND SINGLE(x IN TAIL(NODES(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
            "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
            "WHERE p IN directlyInDiagram AND pe IN interactors " +
            "WITH p, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS occurrences, COLLECT(DISTINCT pe) AS interactsWith " +
            "WHERE inDiagram OR SIZE(occurrences) > 0 " +
            "RETURN DISTINCT p AS diagram, inDiagram, occurrences, interactsWith")
    Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId);

    @Query(" MATCH (i:DatabaseObject{stId:{0}}) " +
            "OPTIONAL MATCH (i)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
            "WHERE re.trivial IS NULL " +
            "WITH COLLECT(DISTINCT pe) AS interactors, i + COLLECT(DISTINCT pe) AS objs " +
            "UNWIND objs as obj " +
            "OPTIONAL MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(obj) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "OPTIONAL MATCH (d:Pathway{hasDiagram:True, stId:{0}}) " +
            "WITH interactors, COLLECT(DISTINCT p) + COLLECT(DISTINCT d) AS directlyInDiagram " +
            "UNWIND directlyInDiagram AS d " +
            "OPTIONAL MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(d) " +
            "WITH interactors, directlyInDiagram, directlyInDiagram + COLLECT(DISTINCT p) AS hlds " +
            "UNWIND hlds AS d " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
            "WITH interactors, directlyInDiagram, hlds + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all AS p " +
            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
            "WHERE ep IN all " +
            "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
            "WHERE sp IN all AND SINGLE(x IN TAIL(NODES(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
            "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
            "WHERE p IN directlyInDiagram AND pe IN interactors " +
            "WITH p, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS occurrences, COLLECT(DISTINCT pe) AS interactsWith " +
            "WHERE inDiagram OR SIZE(occurrences) > 0 " +
            "RETURN DISTINCT p AS diagram, inDiagram, occurrences, interactsWith")
    Collection<DiagramOccurrences> getDiagramOccurrences(String stId);
}
