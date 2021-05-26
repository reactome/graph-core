package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SimpleDatabaseObjectRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public SimpleDatabaseObjectRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }


    public Collection<SimpleDatabaseObject> getPathwaysForIdentifierByStId(String identifier, Collection<String> pathways){
        String query = " " +
                "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
                "WHERE p.stId IN $stIds " +
                "WITH DISTINCT p, pe " +
                "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
                "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
                "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
                "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
                "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:$identifier}) " +
                "WHERE p.stId IN $stIds " +
                "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels";

        Map<String, Object> map = new HashMap<>(2);
        map.put("identifier", identifier);
        map.put("stIds", pathways);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject .class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getPathwaysForIdentifierByDbId(String identifier, Collection<Long> pathways){
        String query = " " +
                "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity) " +
                "WHERE p.dbId IN $stIds " +
                "WITH DISTINCT p, pe " +
                "MATCH (pe)-[:referenceEntity|referenceSequence|crossReference|referenceGene*]->(n)-->(rd:ReferenceDatabase) " +
                "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
                "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels " +
                "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
                "MATCH (p:Pathway)-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output|hasEvent*]->(pe:PhysicalEntity{stId:$identifier}) " +
                "WHERE p.dbId IN $stIds " +
                "RETURN DISTINCT p.dbId AS dbId, p.stId AS stId, p.displayName AS displayName, labels(p) AS labels";

        Map<String, Object> map = new HashMap<>(2);
        map.put("identifier", identifier);
        map.put("stIds", pathways);

        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject .class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByStId(String stId, String identifier) {
        String query = " " +
                "MATCH (t:Pathway{stId:$stId}) " +
                "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
                "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
                "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
                "UNWIND ps AS p " +
                "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
                "WITH DISTINCT rle " +
                "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]-(rle) " +
                "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
                "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
                "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
                "MATCH (t:Pathway{stId:$stId}) " +
                "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
                "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
                "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
                "UNWIND ps AS p " +
                "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
                "WITH DISTINCT rle " +
                "MATCH (rle)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WITH DISTINCT pe " +
                "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
                "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
                "WHERE pe.stId = $identifier OR $identifier IN participants " +
                "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels";

        Map<String, Object> map = new HashMap<>(2);
        map.put("identifier", identifier);
        map.put("stId", stId);

        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject .class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getDiagramEntitiesForIdentifierByDbId(Long dbId, String identifier) {
        String query = " " +
                "MATCH (t:Pathway{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
                "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
                "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
                "UNWIND ps AS p " +
                "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
                "WITH DISTINCT rle " +
                "MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene|hasComponent|hasMember|hasCandidate|repeatedUnit*]-(pe)<-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]-(rle) " +
                "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
                "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels " +
                "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
                "MATCH (t:Pathway{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(t)-[:hasEvent*]->(p:Pathway{hasDiagram:False}) " +
                "WHERE ALL(n IN TAIL(NODES(path)) WHERE n.hasDiagram = False) " +
                "WITH CASE WHEN path IS NULL THEN t ELSE NODES(path) END AS ps " +
                "UNWIND ps AS p " +
                "MATCH (p)-[:hasEvent]->(rle:ReactionLikeEvent) " +
                "WITH DISTINCT rle " +
                "MATCH (rle)-[:input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator*]->(pe:PhysicalEntity) " +
                "WITH DISTINCT pe " +
                "OPTIONAL MATCH (pe)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(a:PhysicalEntity) " +
                "WITH DISTINCT pe, COLLECT(DISTINCT a.stId) AS participants " +
                "WHERE pe.stId = $identifier OR $identifier IN participants " +
                "RETURN DISTINCT pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS displayName, labels(pe) AS labels";

        Map<String, Object> map = new HashMap<>(2);
        map.put("identifier", identifier);
        map.put("dbId", dbId);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject .class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

}
