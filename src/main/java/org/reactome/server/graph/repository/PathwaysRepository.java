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

    @Query(" MATCH (n:DatabaseObject{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH(n:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH(p:Pathway{stId:{0}}), (p)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForByStId(String stId, Long speciesId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION MATCH(n:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(m:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH(p:Pathway{dbId:{0}}), (p)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForByDbId(Long dbId, Long speciesId);



    @Query(" MATCH (:DatabaseObject{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByStId(String stId, Long speciesId);

    @Query(" MATCH (:DatabaseObject{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOfByDbId(Long dbId, Long speciesId);



    @Query(" MATCH (:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH (o:Pathway{stId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{dbId:{1}}) " +
            "WITH o, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByStId(String stId, Long speciesId);

    @Query(" MATCH (:PhysicalEntity{dbId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{dbId:{0}})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels  " +
            "UNION " +
            "MATCH (o:Pathway{dbId:{0}})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{dbId:{1}}) " +
            "WITH o, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForByDbId(Long dbId, Long speciesId);



    @Query(" MATCH (:PhysicalEntity{stId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByStId(String stId, Long speciesId);

    @Query(" MATCH (:PhysicalEntity{dbId:{0}})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{dbId:{1}}) " +
            "WITH r, HEAD(COLLECT(p)) as m " +
            "RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels")
    Collection<SimpleDatabaseObject> getPathwaysWithDiagramForAllFormsOfByDbId(Long dbId, Long speciesId);


    @Query(" MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "WITH DISTINCT pe " +
            "MATCH (pe)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (pe:PhysicalEntity{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{dbId:{1}}) " +
            "RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getLowerLevelPathwaysForIdentifier(String identifier, Long speciesId);


    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
            "WHERE p.stId IN {1} " +
            "WITH DISTINCT p, pe " +
            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "RETURN DISTINCT p.dbId as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:{0}}) " +
            "WHERE p.stId IN {1} " +
            "RETURN DISTINCT p.dbId as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForIdentifierByStId(String identifier, Collection<String> pathways);

    @Query(" MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
            "WHERE p.dbId IN {1} " +
            "WITH DISTINCT p, pe " +
            "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
            "WHERE n.identifier = {0} OR {0} IN n.name OR {0} IN n.geneName " +
            "RETURN DISTINCT p.dbId as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (p:Pathway)-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|catalystActivity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:{0}}) " +
            "WHERE p.dbId IN {1} " +
            "RETURN DISTINCT p.dbId as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
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
            "RETURN DISTINCT(pe.dbId) as dbId, pe.stId as stId, pe.displayName as displayName, labels(pe) as labels " +
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
            "RETURN DISTINCT(pe.dbId) as dbId, pe.stId as stId, pe.displayName as displayName, labels(pe) as labels")
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
            "RETURN DISTINCT(pe.dbId) as dbId, pe.stId as stId, pe.displayName as displayName, labels(pe) as labels " +
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
            "RETURN DISTINCT(pe.dbId) as dbId, pe.stId as stId, pe.displayName as displayName, labels(pe) as labels")
    Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByDbId(Long dbId, String identifier);
}
