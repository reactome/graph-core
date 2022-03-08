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

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public DiagramRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public DiagramResult getDiagramResult(Long dbId) {
        //language=Cypher
        String query = " MATCH (d:Pathway{dbId:$dbId, hasDiagram:true}) " +
                "RETURN d.stId AS diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:true})-[:hasEvent*]->(s:Pathway{dbId:$dbId, hasDiagram:false}) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(s)-[:hasEvent*]->(rle:ReactionLikeEvent) " +
                "WHERE none(x IN nodes(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, s, collect(DISTINCT rle.stId) AS events " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId AS diagramStId, events, d.diagramWidth AS width, d.diagramHeight AS height, size(nodes(depth)) AS level " +
                "ORDER BY level LIMIT 1 " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:true})-[:hasEvent*]->(r:ReactionLikeEvent{dbId:$dbId}) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, r " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId AS diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, size(nodes(depth)) AS level " +
                "ORDER BY level LIMIT 1";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("dbId", dbId)).fetchAs(DiagramResult.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramResult(), record)).one().orElse(null);
    }

    public DiagramResult getDiagramResult(String stId) {
        //language=Cypher
        String query = " MATCH (d:Pathway{stId:$stId, hasDiagram:true}) " +
                "RETURN d.stId AS diagramStId, [] AS events, d.diagramWidth AS width, d.diagramHeight AS height, 1 AS level " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:true})-[:hasEvent*]->(s:Pathway{stId:$stId, hasDiagram:false}) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(s)-[:hasEvent*]->(rle:ReactionLikeEvent) " +
                "WHERE none(x IN nodes(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, s, collect(DISTINCT rle.stId) AS events " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId AS diagramStId, events, d.diagramWidth AS width, d.diagramHeight AS height, size(nodes(depth)) AS level " +
                "ORDER BY level LIMIT 1 " +
                "UNION " +
                "MATCH path=(d:Pathway{hasDiagram:true})-[:hasEvent*]->(r:ReactionLikeEvent{stId:$stId}) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH DISTINCT d, r " +
                "OPTIONAL MATCH depth=shortestPath((tlp:TopLevelPathway)-[:hasEvent*]->(d)) " +
                "WHERE NOT (d:TopLevelPathway) " +
                "RETURN d.stId AS diagramStId, [r.stId] AS events, d.diagramWidth AS width, d.diagramHeight AS height, size(nodes(depth)) AS level " +
                "ORDER BY level LIMIT 1";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("stId", stId)).fetchAs(DiagramResult.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramResult(), record)).one().orElse(null);

    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(Long dbId) {
        //language=Cypher
        String query = "MATCH (root:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(root) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (d:Pathway{hasDiagram:true, dbId:$dbId}) " +
                "WITH root, collect(DISTINCT d) + collect(DISTINCT p) AS directlyInDiagram " +

                "OPTIONAL MATCH (root)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE re.trivial IS NULL " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH root, directlyInDiagram + collect(DISTINCT p) AS directlyInDiagram, collect(DISTINCT pe) AS interactors " +

                "UNWIND directlyInDiagram AS d " +
                "OPTIONAL MATCH (p:Pathway{hasDiagram:true})-[:hasEvent*]->(d) " +
                "WITH interactors, directlyInDiagram, directlyInDiagram + collect(DISTINCT p) AS hlds " +

                "UNWIND hlds AS d " +
                "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
                "WITH interactors, directlyInDiagram, hlds + collect(DISTINCT cep) AS all " +

                "UNWIND all AS p " +
                "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
                "WHERE ep IN all " +
                "OPTIONAL MATCH pathEP=(p)-[:hasEvent*]->(sp:Pathway) " +
                "WHERE sp IN all AND single(x IN tail(nodes(pathEP)) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WHERE p IN directlyInDiagram AND pe IN interactors " +
                "WITH p, p IN directlyInDiagram AS inDiagram, collect(DISTINCT ep.stId) + collect(DISTINCT sp.stId) AS occurrences, collect(DISTINCT pe.stId) AS interactsWith " +

                "WHERE inDiagram OR size(occurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, inDiagram, occurrences, interactsWith";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("dbId", dbId)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (root:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(root) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (d:Pathway{hasDiagram:true, stId:$stId}) " +
                "WITH root, collect(DISTINCT d) + collect(DISTINCT p) AS directlyInDiagram " +

                "OPTIONAL MATCH (root)-[:referenceEntity]->(:ReferenceEntity)<-[:interactor]-(:Interaction)-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE re.trivial IS NULL " +
                "OPTIONAL MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH root, directlyInDiagram + collect(DISTINCT p) AS directlyInDiagram, collect(DISTINCT pe) AS interactors " +

                "UNWIND directlyInDiagram AS d " +
                "OPTIONAL MATCH (p:Pathway{hasDiagram:true})-[:hasEvent*]->(d) " +
                "WITH interactors, directlyInDiagram, directlyInDiagram + collect(DISTINCT p) AS hlds " +

                "UNWIND hlds AS d " +
                "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
                "WITH interactors, directlyInDiagram, hlds + collect(DISTINCT cep) AS all " +

                "UNWIND all AS p " +
                "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
                "WHERE ep IN all " +
                "OPTIONAL MATCH pathEP=(p)-[:hasEvent*]->(sp:Pathway) " +
                "WHERE sp IN all AND single(x IN tail(nodes(pathEP)) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH (p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WHERE p IN directlyInDiagram AND pe IN interactors " +
                "WITH p, p IN directlyInDiagram AS inDiagram, collect(DISTINCT ep.stId) + collect(DISTINCT sp.stId) AS occurrences, collect(DISTINCT pe.stId) AS interactsWith " +

                "WHERE inDiagram OR size(occurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, inDiagram, occurrences, interactsWith";
        ;

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("stId", stId)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }

    public Collection<DiagramOccurrences> getDiagramOccurrencesWithInteractions(String identifier) {
        //language=Cypher
        String query = " " +
                "MATCH (i:ReferenceEntity)<-[:interactor]-()-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
                "WHERE i.identifier = $identifier OR i.variantIdentifier = $identifier " +
                "WITH DISTINCT pe, collect(DISTINCT re) AS res " +
                "MATCH path=(p:Pathway{hasDiagram:true})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe) " +
                "WHERE single(x IN nodes(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH res, collect(DISTINCT p) AS interactorInDiagram " +
                "UNWIND interactorInDiagram AS d " +
                "OPTIONAL MATCH (p:Pathway{hasDiagram:true})-[:hasEvent*]->(d) " +
                "WITH res, interactorInDiagram, interactorInDiagram + collect(DISTINCT p) AS hlds " +
                "UNWIND hlds AS d " +
                "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
                "WITH res, interactorInDiagram, hlds + collect(DISTINCT cep) AS all " +
                "UNWIND all AS p " +
                "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
                "WHERE ep IN all " +
                "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
                "WHERE sp IN all AND single(x IN tail(nodes(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
                "OPTIONAL MATCH aux=(p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(re:ReferenceEntity) " +
                "WHERE p IN interactorInDiagram AND re IN res AND single(x IN nodes(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
                "WITH p, collect(DISTINCT pe.stId) AS pes, collect(DISTINCT ep.stId) + collect(DISTINCT sp.stId) AS pathwaysOccurrences " +
                "WHERE size(pes) > 0 OR size(pathwaysOccurrences) > 0 " +
                "RETURN DISTINCT p.stId AS diagramStId, false AS inDiagram, pathwaysOccurrences AS occurrences, pes AS interactsWith";

        return neo4jClient.query(query).in(databaseName).bindAll(Map.of("identifier", identifier)).fetchAs(DiagramOccurrences.class).mappedBy((t, record) -> ReflectionUtils.build(new DiagramOccurrences(), record)).all();
    }
}