package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.ReflectionUtils;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 */
@Repository
public class DiagramRepository {

    private final Neo4jClient neo4jClient;
    private final Neo4jMappingContext neo4jMappingContext;

    @Autowired
    public DiagramRepository(Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        this.neo4jClient = neo4jClient;
        this.neo4jMappingContext = neo4jMappingContext;
    }

    public DiagramResult getDiagramResult(Long dbId){
        Map<String, Object> map = Map.of("dbId", dbId);
        String query = " MATCH (d:Pathway{dbId:$dbId, hasDiagram:True}) " +
                "RETURN d.stId As diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(s:Pathway{dbId:$dbId, hasDiagram:False}) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(s)-[:hasEvent*]->(rle:ReactionLikeEvent) " +
                "WHERE NONE(x IN NODES(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, s, COLLECT(DISTINCT rle.stId) AS events " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId as diagramStId, events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
                "ORDER BY level LIMIT 1 " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{dbId:$dbId}) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, r " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId as diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
                "ORDER BY level LIMIT 1";

        return neo4jClient.query(query).bindAll(map).fetchAs(DiagramResult.class).mappedBy((t,s) -> ReflectionUtils.build(new DiagramResult(), s)).one().orElse(null);
    }

    public DiagramResult getDiagramResult(String stId) {
        Map<String, Object> map = Map.of("stId", stId);
        String query = " MATCH (d:Pathway{stId:$stId, hasDiagram:True}) " +
                "RETURN d.stId As diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(s:Pathway{stId:$stId, hasDiagram:False}) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(s)-[:hasEvent*]->(rle:ReactionLikeEvent) " +
                "WHERE NONE(x IN NODES(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, s, COLLECT(DISTINCT rle.stId) AS events " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId as diagramStId, events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
                "ORDER BY level LIMIT 1 " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{stId:$stId}) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, r " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId as diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, SIZE(NODES(depth)) AS level " +
                "ORDER BY level LIMIT 1";

        return neo4jClient.query(query).bindAll(map).fetchAs(DiagramResult.class).mappedBy((t,s) -> ReflectionUtils.build(new DiagramResult(), s)).one().orElse(null);

    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId) {
        Map<String, Object> map = Map.of("dbId", dbId);
        String query = " MATCH (i:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH (i)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE re.trivial IS NULL " +
                "WITH COLLECT(DISTINCT pe) AS interactors, i + COLLECT(DISTINCT pe) AS objs " +
                "UNWIND objs as obj " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(obj) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (d:Pathway{hasDiagram:True, dbId:$dbId}) " +
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
                "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WHERE p IN directlyInDiagram AND pe IN interactors " +
                "WITH p, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep.stId) + COLLECT(DISTINCT sp.stId) AS occurrences, COLLECT(DISTINCT pe.stId) AS interactsWith " +
                "WHERE inDiagram OR SIZE(occurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, inDiagram, occurrences, interactsWith";

        return neo4jClient.query(query).bindAll(map).fetchAs(DiagramOccurrences.class).mappedBy((t,s) -> ReflectionUtils.build(new DiagramOccurrences(), s)).all();
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(String stId) {
        Map<String, Object> map = Map.of("stId", stId);
        String query = " MATCH (i:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH (i)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE re.trivial IS NULL " +
                "WITH COLLECT(DISTINCT pe) AS interactors, i + COLLECT(DISTINCT pe) AS objs " +
                "UNWIND objs as obj " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(obj) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (d:Pathway{hasDiagram:True, stId:$stId}) " +
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
                "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WHERE p IN directlyInDiagram AND pe IN interactors " +
                "WITH p, p IN directlyInDiagram AS inDiagram, COLLECT(DISTINCT ep.stId) + COLLECT(DISTINCT sp.stId) AS occurrences, COLLECT(DISTINCT pe.stId) AS interactsWith " +
                "WHERE inDiagram OR SIZE(occurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, inDiagram, occurrences, interactsWith";

        return neo4jClient.query(query).bindAll(map).fetchAs(DiagramOccurrences.class).mappedBy((t,s) -> ReflectionUtils.build(new DiagramOccurrences(), s)).all();
    }
}
