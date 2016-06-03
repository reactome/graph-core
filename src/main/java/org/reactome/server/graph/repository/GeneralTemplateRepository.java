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
public class GeneralTemplateRepository {

    private static final Logger logger = LoggerFactory.getLogger(GeneralTemplateRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadByProperty(clazz,property, value, depth);
    }

    public <T> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadAllByProperty(clazz, property, value, depth);
    }



    public DatabaseObject findEnhancedObjectById(Long dbId, String relationships) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}})-[r1]-(m) OPTIONAL MATCH (m)-[r2:regulator|regulatedBy|catalyzedEvent|physicalEntity]-(o) RETURN n,r1,m,r2,o";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }



    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public DatabaseObject findEnhancedObjectById(Long dbId) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}})-[r1]-(m) OPTIONAL MATCH (m)-[r2:regulator|regulatedBy|catalyzedEvent|physicalEntity]-(o) RETURN n,r1,m,r2,o";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public DatabaseObject findEnhancedObjectById(String stId) {
        String query = "Match (n:DatabaseObject{stId:{stId}})-[r1]-(m) OPTIONAL MATCH (m)-[r2:regulator|regulatedBy|catalyzedEvent|physicalEntity]-(o) RETURN n,r1,m,r2,o";
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public DatabaseObject findById(Long dbId, RelationshipDirection direction) {
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

    public DatabaseObject findById(String stId, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject{stId:{stId}})-[r]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject{stId:{stId}})<-[r]-(m) RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject{stId:{stId}})-[r]-(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public DatabaseObject findById (Long dbId, RelationshipDirection direction, String... relationships) {
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

    public DatabaseObject findById (String stId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stId:{stId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stId:{stId}})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{stId:{stId}})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stId",stId);
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

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject)-[r]->(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject)<-[r]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject)-[r]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stIds", stIds);
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

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) WHERE n.stId IN {stIds} RETURN n,r,m";
                break;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("stIds", stIds);
        Result result =  neo4jTemplate.query(query, map);
        Set<DatabaseObject> databaseObjects = new HashSet<>();
        for (Map<String, Object> stringObjectMap : result) {
            databaseObjects.add((DatabaseObject) stringObjectMap.get("n"));
        }
        return databaseObjects;
    }

    // ---------------------------------------- Class Level Operations -------------------------------------------------

    // Find by Class Name

    public <T> Collection<T> findObjectsByClassName(Class<T> clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, Collections.emptyMap());
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
        Result result = neo4jTemplate.query(query, Collections.emptyMap());
        Collection<String> simpleRefs = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : result) {
            simpleRefs.add(stringObjectMap.get("dbId") + "\t" + stringObjectMap.get("databaseName") + ":" + stringObjectMap.get("identifier"));
        }
        return simpleRefs;
    }

    public Long countEntries(Class<?> clazz) {
        return neo4jTemplate.count(clazz);
    }


    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    public Result query (String query, Map<String,Object> map) {
        return neo4jTemplate.query(query,map);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

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

    public void delete(String stId) {
        String query = "MATCH (n:DatabaseObject{stId:{stId}}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        neo4jTemplate.query(query, map);
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------

    public boolean fitForService() {
        String query = "Match (n) Return Count(n)>0 AS fitForService";
        try {
            Result result = neo4jTemplate.query(query, Collections.emptyMap());
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





//    TOO DANGEROUS works if you specify the "right" relationships
//    public DatabaseObject test (Long dbId, String... relationships) {
//        String query = "Match (n:DatabaseObject{dbId:51925})-[r1]-(m) OPTIONAL MATCH (n)-[" +
//                RepositoryUtils.getRelationshipAsString(relationships) +
//                "]-()-[r2]-(l) RETURN n,r,m,r2,l";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result =  neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (DatabaseObject) result.iterator().next().get("n");
//        return null;
//    }
//
//
//    public DatabaseObject test (String stId, String... relationships) {
//        String query = "Match (n:DatabaseObject{stId:51925})-[r1]-(m) OPTIONAL MATCH (n)-[" +
//                RepositoryUtils.getRelationshipAsString(relationships) +
//                "]-()-[r2]-(l) RETURN n,r1,m,r2,l";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stId", stId);
//        Result result =  neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (DatabaseObject) result.iterator().next().get("n");
//        return null;
//    }


// Finder Methods without Relationships
//    todo these can be found in DatabaseObjectRepository

//    public <T> T findByDbIdNoRelations(Class<T> clazz, Long dbId) {
//        String query = "Match (n:DatabaseObject{dbId:{dbId}}) RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        return neo4jTemplate.queryForObject(clazz, query, map);
//    }
//
//    public <T> T findByStIdNoRelations(Class<T> clazz, String stId) {
//        String query = "Match (n:DatabaseObject{stId:{stId}}) RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stId", stId);
//        return neo4jTemplate.queryForObject(clazz, query, map);
//    }
//
//    public <T> Iterable<T> findByDbIdsNoRelations(Class<T> clazz, Collection<Long> dbIds) {
//        String query = "Match (n:DatabaseObject) WHERE n.dbId IN {dbIds} RETURN n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbIds", dbIds);
//        return neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    public <T> Iterable<T> findByStIdsNoRelations(Class<T> clazz, Collection<String> stId) {
//        String query = "Match (n:DatabaseObject) WHERE n.stId IN {stId} RETURN n";
//        Map<String, Object> map = new HashMap<>();
//        map.put("stId", stId);
//        return neo4jTemplate.queryForObjects(clazz, query, map);
//    }
