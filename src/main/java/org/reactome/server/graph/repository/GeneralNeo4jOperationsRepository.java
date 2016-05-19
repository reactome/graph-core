package org.reactome.server.graph.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.util.RepositoryUtils;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@SuppressWarnings("unused")
@Repository
public class GeneralNeo4jOperationsRepository {

    private static final Logger logger = LoggerFactory.getLogger(GeneralNeo4jOperationsRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    // Find by property and depth

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadByProperty(clazz,property, value, depth);
    }

    public <T> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadAllByProperty(clazz, property, value, depth);
    }

//    todo Method is currently broken report to SDN
    @Deprecated
    public <T> Collection<T> findByProperties(Class<T> clazz, String property, Collection<Object> values, Integer depth) {
        return neo4jTemplate.loadAllByProperty(clazz,property, values, depth);
    }

    // Default Finder Methods

////    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByDbId(Class<T> clazz, Long dbId) {
//        String query = "Match (n:DatabaseObject{dbId:{dbId}})-[r]-(m) RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        return neo4jTemplate.queryForObject(clazz, query, map);
//    }
//
////    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier) {
//        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r]-(m) RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stableIdentifier", stableIdentifier);
//        return neo4jTemplate.queryForObject(clazz, query, map);
//    }
//
////    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByDbIds(Class<T> clazz, Collection<Long> dbIds) {
//        String query = "Match (n:DatabaseObject)-[r]-(m) WHERE n.dbId IN {dbIds} RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbIds", dbIds);
//        return neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
////    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByStableIdentifiers(Class<T> clazz, Collection<String> stableIdentifiers) {
//        String query = "Match (n:DatabaseObject)-[r]-(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stableIdentifiers", stableIdentifiers);
//        return neo4jTemplate.queryForObjects(clazz, query, map);
//    }

    // Finder Methods without Relationships

    public <T> T findByDbIdNoRelations(Class<T> clazz, Long dbId) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}}) RETURN n";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        return neo4jTemplate.queryForObject(clazz, query, map);
    }

    public <T> T findByStableIdentifierNoRelations(Class<T> clazz, String stableIdentifier) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}}) RETURN n";
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stableIdentifier);
        return neo4jTemplate.queryForObject(clazz, query, map);
    }

    public <T> Iterable<T> findByDbIdsNoRelations(Class<T> clazz, Collection<Long> dbIds) {
        String query = "Match (n:DatabaseObject) WHERE n.dbId IN {dbIds} RETURN n";
        Map<String,Object> map = new HashMap<>();
        map.put("dbIds", dbIds);
        return neo4jTemplate.queryForObjects(clazz, query, map);
    }

    public <T> Iterable<T> findByStableIdentifiersNoRelations(Class<T> clazz, Collection<String> stableIdentifiers) {
        String query = "Match (n:DatabaseObject) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n";
        Map<String, Object> map = new HashMap<>();
        map.put("stableIdentifiers", stableIdentifiers);
        return neo4jTemplate.queryForObjects(clazz, query, map);
    }

    // Finder Methods with RelationshipDirection and Relationships

    public DatabaseObject findByDbId(Long dbId, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject{dbId:{dbId}})-[r]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r]-(m) RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject{dbId:{dbId}})-[r]-(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public DatabaseObject findByStableIdentifier(String stableIdentifier, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r]-(m) RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r]-(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stableIdentifier);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public DatabaseObject findByDbId (Long dbId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbId",dbId);
        Result result =  neo4jTemplate.query(query,map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public DatabaseObject findByStableIdentifier (String stableIdentifier, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier",stableIdentifier);
        Result result =  neo4jTemplate.query(query,map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject)-[r]->(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject)<-[r]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject)-[r]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbIds", dbIds);
        Result result =  neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByStableIdentifiers(Collection<String> stableIdentifiers, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject)-[r]->(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject)<-[r]-(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject)-[r]-(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifiers", stableIdentifiers);
        Result result =  neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.dbId IN {dbIds} RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbIds", dbIds);
        Result result =  neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    public Collection<DatabaseObject> findByStableIdentifiers(Collection<String> stableIdentifiers, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stableIdentifier IN {stableIdentifiers} RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifiers", stableIdentifiers);
        Result result =  neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    // Find by Class Name

    public <T> Collection<T> findObjectsByClassName(Class<T> clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, Collections.<String, Object>emptyMap());
    }

    public <T> Collection<T> findObjectsByClassName(Class<T> clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, map);
    }

    // Find Simple ReferenceObjects

    public Collection<String> findSimpleReferencesByClassName(String className) {
        String query = "Match (n:" + className + ") RETURN n.dbId AS dbId, n.databaseName AS databaseName, n.identifier AS identifier ";
        Result result = neo4jTemplate.query(query, Collections.<String, Object>emptyMap());
        Collection<String> simpleRefs = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : result) {
            simpleRefs.add(stringObjectMap.get("dbId") + "\t" + stringObjectMap.get("databaseName") + ":" + stringObjectMap.get("identifier"));
        }
        return simpleRefs;
    }

    // Save and Delete

    public <T extends DatabaseObject> T save(T t) {
        return neo4jTemplate.save(t);
    }

    public <T extends DatabaseObject> T save(T t, int depth) {
        return neo4jTemplate.save(t, depth);
    }

    public void delete(Object o)  {
        neo4jTemplate.delete(o);
    }

    public void delete(Long dbId) {
        String query = "MATCH (n:DatabaseObject{dbId:{dbId}}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        neo4jTemplate.query(query, map);
    }

    public void delete(String stableIdentifier) {
        String query = "MATCH (n:DatabaseObject{stableIdentifier:{stableIdentifier}}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stableIdentifier);
        neo4jTemplate.query(query, map);
    }

    // Method for querying without mapping to Objects

    public Result query (String query, Map<String,Object> map) {
        return neo4jTemplate.query(query,map);
    }

    // Count entries of class

    public Long countEntries(Class<?> clazz) {
        return neo4jTemplate.count(clazz);
    }

    // Utility Methods used in JUnit Tests

    public boolean fitForService() {
        String query = "Match (n) Return Count(n)>0 AS fitForService";
        try {
            Result result = neo4jTemplate.query(query, Collections.<String, Object>emptyMap());
            if (result != null && result.iterator().hasNext())
                return (boolean) result.iterator().next().get("fitForService");
        } catch (Exception e) {
            logger.error("A connection with the Neo4j Graph could not be established. Tests will be skipped", e);
        }
        return false;
    }

    public void clearCache() {
        neo4jTemplate.clear();
    }



//    //TODO
//    public Pathway getEventHierarchy(Long dbId) {
//        String query = "Match (n:Event{dbId:{dbId}})-[r:hasEvent*]->(m:Event) return n,r,m";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result =  neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (Pathway) result.iterator().next().get("n");
//        return null;
//    }
//
//    //TODO
//    public Object getSomeHierarchy(Long dbId) {
//        String query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r:hasComponent|hasMember|input|output|hasEvent*]-(m) " +
//                "RETURN n.dbId,n.stableIdentifier,n.displayName, " +
//                "EXTRACT(rel IN r | [endNode(rel).dbId, endNode(rel).stableIdentifier, endNode(rel).displayName]) as nodePairCollection";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result =  neo4jTemplate.query(query, map);
//        return null;
//    }
//
//
//    //TODO
//    public DatabaseObject getLocationsHierarchy(String stId) {
//        String query = "Match (n:DatabaseObject{dbId:373624})<-[r:regulatedBy|regulator|physicalEntity|catalystActivity|hasMember|hasComponent|input|output|hasEvent*]-(m) Return n,r,m";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stableIdentifier", stId);
//        Result result =  neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (DatabaseObject) result.iterator().next().get("n");
//        return null;
//    }



//    //TODO
//    public DatabaseObject getReferral(Long dbId, String relationshipName) {
//
//        String query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r:" + relationshipName + "]-(m) Return n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result =  neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (DatabaseObject) result.iterator().next().get("n");
//        return null;
//    }
//    //TODO
//    public Collection<DatabaseObject> getReferrals(Long dbId, String relationshipName) {
//
//        String query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r:" + relationshipName + "]-(m) Return n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result =  neo4jTemplate.query(query, map);
//        List<DatabaseObject> referrers = new ArrayList<>();
//        for (Map<String, Object> stringObjectMap : result) {
//            referrers.add((DatabaseObject) stringObjectMap.get("n"));
//        }
//        return referrers;
//    }
//
//    public Collection<DatabaseObject> findCollectionByPropertyWithRelationships (String property, Collection<Object> values, String... relationships) {
//
//
//        String query = "Match (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n." + property + " IN {values} RETURN n,r,m";
//        Map<String,Object> map = new HashMap<>();
//        map.put("values", values);
//        Result result = neo4jTemplate.query( query, map);
//        List<DatabaseObject> databaseObjects = new ArrayList<>();
//        for (Map<String, Object> stringObjectMap : result) {
//            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
//        }
//        return databaseObjects;
//
//    }



    //TODO
    //Match (n:DatabaseObject{stableIdentifier:"R-HSA-445133"})<-[r:hasMember|hasComponent|input|output|hasEvent*]-(m) Return EXTRACT(rel IN r | [endNode(rel).dbId, endNode(rel).stableIdentifier, endNode(rel).displayName, endNode(rel).hasDiagram ]) as nodePairCollection
    //TODO
    //Match (n:DatabaseObject{stableIdentifier:"R-HSA-445133"})<-[r:regulatedBy|regulator|physicalEntity|catalystActivity|hasMember|hasComponent|input|output|hasEvent*]-(m) Return EXTRACT(rel IN r | [endNode(rel).dbId, endNode(rel).stableIdentifier, endNode(rel).displayName, labels(endNode(rel))  ]) as nodePairCollection





}
