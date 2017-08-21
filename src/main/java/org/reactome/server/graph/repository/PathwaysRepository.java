package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface PathwaysRepository extends GraphRepository<DatabaseObject> {

    @Query("MATCH (:Pathway{stId:{0}})-[:hasEvent*]->(e:Event) RETURN e")
    Collection<Event> getContainedEventsByStId(String stId);

    @Query("MATCH (:Pathway{dbId:{0}})-[:hasEvent*]->(e:Event) RETURN e")
    Collection<Event> getContainedEventsByDbId(Long dbId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (n:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(m:Pathway) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (p:Pathway{stId:{0}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForByStId(String stId);

    @Query(" MATCH (n:DatabaseObject{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (n:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (p:Pathway{stId:{0}})-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForByStIdAndSpeciesTaxId(String stId, String taxId);


    @Query(" MATCH (n:DatabaseObject{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (n:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(m:Pathway) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (p:Pathway{dbId:{0}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForByDbId(Long dbId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (n:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (p:Pathway{dbId:{0}})-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForByDbIdAndSpeciesTaxId(Long dbId, String taxId);


    @Query(" MATCH (:DatabaseObject{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByStId(String stId);

    @Query(" MATCH (:DatabaseObject{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByStIdAndSpeciesTaxId(String stId, String taxId);


    @Query(" MATCH (:DatabaseObject{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByDbId(Long dbId);

    @Query(" MATCH (:DatabaseObject{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByDbIdAndSpeciesTaxId(Long dbId, String taxId);


    @Query(" MATCH (:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (o:Pathway{stId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByStId(String stId);

    @Query(" MATCH (:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (o:Pathway{stId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:{1}}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByStIdAndSpeciesTaxId(String stId, String taxId);


    @Query(" MATCH (:PhysicalEntity{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels  " +
            "UNION " +
            "MATCH (o:Pathway{dbId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByDbId(Long dbId);

    @Query(" MATCH (:PhysicalEntity{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels  " +
            "UNION " +
            "MATCH (o:Pathway{dbId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:{1}}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByDbIdAndSpeciesTaxId(Long dbId, String taxId);


    @Query(" MATCH (:PhysicalEntity{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByStId(String stId);

    @Query(" MATCH (:PhysicalEntity{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId(String stId, String taxId);


    @Query(" MATCH (:PhysicalEntity{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByDbId(Long dbId);

    @Query(" MATCH (:PhysicalEntity{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m.dbId AS dbId, m.stId AS stId, m.displayName AS displayName, labels(m) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByDbIdAndSpeciesTaxId(Long dbId, String taxId);


    @Query(" MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "WITH DISTINCT pe " +
            "MATCH (pe)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (pe:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getLowerLevelPathwaysForIdentifier(String identifier);

    @Query(" MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "WITH DISTINCT pe " +
            "MATCH (pe)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (pe:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:{1}}) " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getLowerLevelPathwaysForIdentifierAndSpeciesTaxId(String identifier, String taxId);


    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
            "WHERE p.stId IN {1} " +
            "WITH DISTINCT p, pe " +
            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:{0}}) " +
            "WHERE p.stId IN {1} " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForIdentifierByStId(String identifier, Collection<String> pathways);

    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
            "WHERE p.dbId IN {1} " +
            "WITH DISTINCT p, pe " +
            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:{0}}) " +
            "WHERE p.dbId IN {1} " +
            "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels")
    Collection<SimpleDatabaseObject> getPathwaysForIdentifierByDbId(String identifier, Collection<Long> pathways);


    @Query(" MATCH (t:Pathway{stId:{0}}) " +
            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
            "UNWIND ps AS p " +
            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT rle " +
            "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]-(rle) " +
            "WHERE n.identifier = {1} OR {1} IN n.name OR {1} IN n.geneName " +
            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (t:Pathway{stId:{0}}) " +
            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
            "UNWIND ps AS p " +
            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT rle " +
            "MATCH (rle)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
            "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
            "WHERE pe.stId = {1} OR {1} IN participants " +
            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels")
    Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByStId(String stId, String identifier);

    @Query(" MATCH (t:Pathway{dbId:{0}}) " +
            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
            "UNWIND ps AS p " +
            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT rle " +
            "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]-(rle) " +
            "WHERE n.identifier = {1} OR {1} IN n.name OR {1} IN n.geneName " +
            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (t:Pathway{dbId:{0}}) " +
            "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
            "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
            "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
            "UNWIND ps AS p " +
            "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
            "WITH DISTINCT rle " +
            "MATCH (rle)-[:input|output|catalystActivity|entityFunctionalStatus|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
            "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
            "WHERE pe.stId = {1} OR {1} IN participants " +
            "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels")
    Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByDbId(Long dbId, String identifier);
}
