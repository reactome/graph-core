package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MappingRepository extends Neo4jRepository<Event, Long> {

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower($databaseName) AND (n.identifier = $identifier OR n.variantIdentifier = $identifier OR $identifier IN n.geneName OR $identifier IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(@Param("databaseName") String databaseName, @Param("identifier") String identifier);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower($databaseName) AND (n.identifier = $identifier OR n.variantIdentifier = $identifier OR $identifier IN n.geneName OR $identifier IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (:Species{taxId:$taxId})<-[:species]-(rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "RETURN DISTINCT rle " +
            "ORDER BY rle.stId")
    Collection<ReactionLikeEvent> getReactionsLikeEvent(@Param("databaseName") String databaseName, @Param("identifier") String identifier, @Param("taxId") String taxId);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower($databaseName) AND (n.identifier = $identifier OR n.variantIdentifier = $identifier OR $identifier IN n.geneName OR $identifier IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "WITH DISTINCT rle " +
            "MATCH (p:Pathway)-[:hasEvent]->(rle) " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getPathways(@Param("databaseName") String databaseName, @Param("identifier") String identifier);

    @Query(" MATCH (n)-[:referenceDatabase]->(rd:ReferenceDatabase) " +
            "WHERE toLower(rd.displayName) = toLower($databaseName) AND (n.identifier = $identifier OR n.variantIdentifier = $identifier OR $identifier IN n.geneName OR $identifier IN n.name) " +
            "WITH DISTINCT n " +
            "MATCH (pe:PhysicalEntity)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n) " +
            "WITH DISTINCT pe " +
            "MATCH (rle:ReactionLikeEvent)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe) " +
            "WITH DISTINCT rle " +
            "MATCH (:Species{taxId:$taxId})<-[:species]-(p:Pathway)-[:hasEvent]->(rle) " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getPathways(@Param("databaseName") String databaseName, @Param("identifier") String identifier, @Param("taxId") String taxId);

    @Query(" MATCH (p:Pathway)-[:goBiologicalProcess*]->(go:GO_BiologicalProcess) " +
            "WHERE go.accession = $identifier " +
            "RETURN DISTINCT p " +
            "ORDER BY p.stId")
    Collection<Pathway> getGoPathways(@Param("identifier") String identifier);

    @Query(" MATCH (p:Pathway)-[:goBiologicalProcess*]->(go:GO_BiologicalProcess) " +
            "WHERE go.accession = $identifier " +
            "WITH DISTINCT p " +
            "MATCH (p)-[:species]->(:Species{taxId: $taxId}) " +
            "RETURN p " +
            "ORDER BY p.stId")
    Collection<Pathway> getGoPathways(@Param("identifier") String identifier, @Param("taxId") String taxId);
}
