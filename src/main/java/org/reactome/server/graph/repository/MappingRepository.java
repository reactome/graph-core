package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface MappingRepository extends GraphRepository<Event> {

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (:Species{taxId:{2}})<-[:species]-(rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(String databaseName, String identifier, String taxId);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower({0}) AND (n.identifier = {1} OR n.variantIdentifier = {1} OR {1} IN n.geneName OR {1} IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
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
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "WITH DISTINCT rle " +
            "MATCH (:Species{taxId:{2}})<-[:species]-(p:Pathway)-[:hasEvent]->(rle) " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getPathways(String databaseName, String identifier, String taxId);
}
