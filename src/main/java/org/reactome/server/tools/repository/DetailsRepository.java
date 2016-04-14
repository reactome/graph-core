package org.reactome.server.tools.repository;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@Repository
public class DetailsRepository {

    @Autowired
    private Neo4jOperations neo4jTemplate;


    public DatabaseObject findByDbId(Long dbId, RelationshipDirection direction) {
        String query;
        if (direction.equals(RelationshipDirection.INCOMING)) {
            query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r]-(m) RETURN n,r,m";
        } else {
            query = "Match (n:DatabaseObject{dbId:{dbId}})-[r]->(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }


    public DatabaseObject findByStableIdentifier(String stId, RelationshipDirection direction) {
        String query;
        if (direction.equals(RelationshipDirection.INCOMING)) {
            query = "Match (n:DatabaseObject{stableIdentifier:{stId}})<-[r]-(m) RETURN n,r,m";
        } else {
            query = "Match (n:DatabaseObject{stableIdentifier:{stId}})-[r]->(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public <T> Collection<T> getObjectsByClassName(Class<T> clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" +
                clazz.getSimpleName() +
                ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";

        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, map);
    }

    public Result getLocationsInPathwayBrowser(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return  EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
        Result result =  neo4jTemplate.query(query, map);
        return result;
    }

}