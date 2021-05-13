package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MappingRepository extends Neo4jRepository<Event, Long> {

    // TODO Test Mapping Repository ... likely to Slit the events
    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (:Species{taxId:{2}})<-[:species]-(rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier, String taxId);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "WITH DISTINCT rle " +
            "MATCH (p:Pathway)-[:hasEvent]->(rle) " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getPathways(String databaseName, String identifier);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "WITH DISTINCT rle " +
            "MATCH (:Species{taxId:{2}})<-[:species]-(p:Pathway)-[:hasEvent]->(rle) " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getPathways(String databaseName, String identifier, String taxId);

    @Query(" MATCH (p:Pathway)-[:goBiologicalProcess*]->(go:GO_BiologicalProcess) " +
            "WHERE go.accession = {0} " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getGoPathways(String identifier);

    @Query(" MATCH (p:Pathway)-[:goBiologicalProcess*]->(go:GO_BiologicalProcess) " +
            "WHERE go.accession = {0} " +
            "WITH DISTINCT p " +
            "MATCH (p)-[:species]->(:Species{taxId: {1}}) " +
            "RETURN p " +
            "ORDER BY p.stId")
    Collection<Pathway> getGoPathways(String identifier, String taxId);
}
