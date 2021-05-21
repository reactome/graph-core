package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.ReflectionUtils;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class DiagramRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database}")
    private String databaseName;

    @Autowired
    public DiagramRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public DiagramResult getDiagramResult(Long dbId) {
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

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("dbId", dbId)).fetchAs(DiagramResult.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramResult(), record)).one().orElse(null);
    }

    public DiagramResult getDiagramResult(String stId) {
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

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("stId", stId)).fetchAs(DiagramResult.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramResult(), record)).one().orElse(null);

    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId) {
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

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("dbId", dbId)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(String stId) {
        String query = " " +
                "MATCH (i:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH (i)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE re.trivial IS NULL " +
                "WITH collect(DISTINCT pe) AS interactors, i + collect(DISTINCT pe) AS objs " +
                "UNWIND objs AS obj " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(obj) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (d:Pathway{hasDiagram:true, stId:$stId}) " +
                "WITH interactors, collect(DISTINCT p) + collect(DISTINCT d) AS directlyInDiagram " +
                "UNWIND directlyInDiagram AS d " +
                "OPTIONAL MATCH (p:Pathway{hasDiagram:true})-[:hasEvent*]->(d) " +
                "WITH interactors, directlyInDiagram, directlyInDiagram + collect(DISTINCT p) AS hlds " +
                "UNWIND hlds AS d " +
                "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
                "WITH interactors, directlyInDiagram, hlds + collect(DISTINCT cep) AS all " +
                "UNWIND all AS p " +
                "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
                "WHERE ep IN all " +
                "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
                "WHERE sp IN all AND single(x IN tail(nodes(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WHERE p IN directlyInDiagram AND pe IN interactors " +
                "WITH p, p IN directlyInDiagram AS inDiagram, collect(DISTINCT ep.stId) + collect(DISTINCT sp.stId) AS occurrences, collect(DISTINCT pe.stId) AS interactsWith " +
                "WHERE inDiagram OR size(occurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, inDiagram, occurrences, interactsWith";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("stId", stId)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }

    public Collection<DiagramOccurrences> getDiagramOccurrencesWithInteractions(String identifier) {
        String query = " " +
                "MATCH (i:ReferenceEntity)<-[:interactor]-()-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE i.identifier = $identifier OR i.variantIdentifier = $identifier " +
                "WITH DISTINCT pe, COLLECT(DISTINCT re) AS res " +
                "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH res, COLLECT(DISTINCT p) AS interactorInDiagram " +
                "UNWIND interactorInDiagram AS d " +
                "OPTIONAL MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(d) " +
                "WITH res, interactorInDiagram, interactorInDiagram + COLLECT(DISTINCT p) AS hlds " +
                "UNWIND hlds AS d " +
                "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
                "WITH res, interactorInDiagram, hlds + COLLECT(DISTINCT cep) AS all " +
                "UNWIND all AS p " +
                "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
                "WHERE ep IN all " +
                "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
                "WHERE sp IN all AND SINGLE(x IN TAIL(NODES(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(re:ReferenceEntity) " +
                "WHERE p IN interactorInDiagram AND re IN res AND SINGLE(x IN NODES(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH p, COLLECT(DISTINCT pe.stId) AS pes, COLLECT(DISTINCT ep.stId) + COLLECT(DISTINCT sp.stId) AS pathwaysOccurrences " +
                "WHERE SIZE(pes) > 0 OR SIZE(pathwaysOccurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, false AS inDiagram, pathwaysOccurrences AS occurrences, pes AS interactsWith";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("identifier", identifier)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }
}