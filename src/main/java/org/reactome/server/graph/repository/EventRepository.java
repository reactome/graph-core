package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface EventRepository extends Neo4jRepository<Event, Long> {

    @Query("MATCH (p:Pathway)-[r:hasEvent*]->(e:Event) WHERE p.stId = $stId RETURN e, collect(r), collect(p)")
    Collection<Event> getContainedEventsByStId(@Param("stId") String stId);

    @Query("MATCH (p:Pathway)-[r:hasEvent*]->(e:Event) WHERE p.dbId = $dbId RETURN e, collect(r), collect(p)")
    Collection<Event> getContainedEventsByDbId(@Param("dbId") Long dbId);

//    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
//            "WHERE p.stId IN {1} " +
//            "WITH DISTINCT p, pe " +
//            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
//            "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
//            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
//            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
//            "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:$stId}) " +
//            "WHERE p.stId IN {1} " +
//            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
//    Collection<SimpleDatabaseObject> getPathwaysForIdentifierByStId(String identifier, Collection<String> pathways);
//
//    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
//            "WHERE p.dbId IN {1} " +
//            "WITH DISTINCT p, pe " +
//            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
//            "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
//            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
//            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
//            "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:$stId}) " +
//            "WHERE p.dbId IN {1} " +
//            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
//    Collection<SimpleDatabaseObject> getPathwaysForIdentifierByDbId(String identifier, Collection<Long> pathways);


//    @Query(" MATCH (t:Pathway{stId:$stId}) " +
//            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
//            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
//            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
//            "UNWIND ps AS p " +
//            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
//            "WITH DISTINCT rle " +
//            "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]-(rle) " +
//            "WHERE n.identifier = {1} OR {1} IN n.name OR {1} IN n.geneName " +
//            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
//            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
//            "MATCH (t:Pathway{stId:$stId}) " +
//            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
//            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
//            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
//            "UNWIND ps AS p " +
//            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
//            "WITH DISTINCT rle " +
//            "MATCH (rle)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
//            "WITH DISTINCT pe " +
//            "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
//            "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
//            "WHERE pe.stId = {1} OR {1} IN participants " +
//            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels")
//    Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByStId(String stId, String identifier);
//
//    @Query(" MATCH (t:Pathway{dbId:$dbId}) " +
//            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
//            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
//            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
//            "UNWIND ps AS p " +
//            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
//            "WITH DISTINCT rle " +
//            "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]-(rle) " +
//            "WHERE n.identifier = {1} OR {1} IN n.name OR {1} IN n.geneName " +
//            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
//            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
//            "MATCH (t:Pathway{dbId:$dbId}) " +
//            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
//            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
//            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
//            "UNWIND ps AS p " +
//            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
//            "WITH DISTINCT rle " +
//            "MATCH (rle)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
//            "WITH DISTINCT pe " +
//            "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
//            "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
//            "WHERE pe.stId = {1} OR {1} IN participants " +
//            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels")
//    Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByDbId(Long dbId, String identifier);
}
