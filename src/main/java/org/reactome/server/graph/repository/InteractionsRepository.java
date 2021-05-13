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

//    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
//            "WHERE a.identifier = $acc OR a.variantIdentifier = $acc " +
//            "MATCH path=(p:Pathway)-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
//            "WHERE p.speciesName = $speciesName AND SINGLE(e IN NODES(path) WHERE (e:Pathway)) " +
//            "RETURN DISTINCT p")
//    Collection<Pathway> getLowerLevelPathways(@Param("acc") String acc, @Param("speciesName") String speciesName);
//
//    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
//            "WHERE a.identifier = $acc OR a.variantIdentifier = $acc " +
//            "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
//            "WHERE p.speciesName = $speciesName AND SINGLE(e IN NODES(path) WHERE (e:Pathway) AND e.hasDiagram) " +
//            "RETURN DISTINCT p")
//    Collection<Pathway> getDiagrammedLowerLevelPathways(@Param("acc") String acc, @Param("speciesName") String speciesName);

//    @Query(" MATCH (i:ReferenceEntity)<-[:interactor]-()-[:interactor]->(re:ReferenceEntity)<-[:referenceEntity]-(pe:PhysicalEntity) " +
//            "WHERE i.identifier = $identifier OR i.variantIdentifier = $identifier " +
//            "WITH DISTINCT pe, COLLECT(DISTINCT re) AS res " +
//            "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe) " +
//            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
//            "WITH res, COLLECT(DISTINCT p) AS interactorInDiagram " +
//            "UNWIND interactorInDiagram AS d " +
//            "OPTIONAL MATCH (p:Pathway{hasDiagram:True})-[:hasEvent*]->(d) " +
//            "WITH res, interactorInDiagram, interactorInDiagram + COLLECT(DISTINCT p) AS hlds " +
//            "UNWIND hlds AS d " +
//            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(d) " +
//            "WITH res, interactorInDiagram, hlds + COLLECT(DISTINCT cep) AS all " +
//            "UNWIND all AS p " +
//            "OPTIONAL MATCH (p)-[:hasEncapsulatedEvent]->(ep:Pathway) " +
//            "WHERE ep IN all " +
//            "OPTIONAL MATCH path=(p)-[:hasEvent*]->(sp:Pathway) " +
//            "WHERE sp IN all AND SINGLE(x IN TAIL(NODES(path)) WHERE (x:Pathway) AND x.hasDiagram) " +
//            "OPTIONAL MATCH aux=(p)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(re:ReferenceEntity) " +
//            "WHERE p IN interactorInDiagram AND re IN res AND SINGLE(x IN NODES(aux) WHERE (x:Pathway) AND x.hasDiagram) " +
//            "WITH p, COLLECT(DISTINCT pe) AS pes, COLLECT(DISTINCT ep) + COLLECT(DISTINCT sp) AS pathwaysOccurrences " +
//            "WHERE SIZE(pes) > 0 OR SIZE(pathwaysOccurrences) > 0 " +
//            "RETURN DISTINCT p AS diagram, false AS inDiagram, pathwaysOccurrences AS occurrences, pes AS interactsWith")
//    Collection<DiagramOccurrences> getDiagramOccurrences(@Param("identifier") String identifier);
}
