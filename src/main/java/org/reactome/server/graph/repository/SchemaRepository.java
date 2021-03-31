//package org.reactome.server.graph.repository;
//
//import org.neo4j.ogm.model.Result;
//import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
//import org.reactome.server.graph.domain.result.SimpleReferenceObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.neo4j.template.Neo4jOperations;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
///**
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @author Antonio Fabregat (fabregat@ebi.ac.uk)
// */
//@Repository
//public class SchemaRepository {
//
//    @Autowired
//    private Neo4jOperations neo4jTemplate;
//
//    // ---------------------------------------- Query by Class --------------------------------------------------
//
//    public <T> Collection<T> getByClass(Class<T> clazz) {
//        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
//        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, Collections.emptyMap());
//    }
//
//    public <T> Collection<T> getByClassAndSpeciesTaxId(Class<T> clazz, String taxId) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        return (Collection<T>)  neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    public <T> Collection<T> getByClassAndSpeciesName(Class<T> clazz, String speciesName) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        return (Collection<T>)  neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    // ------------------------------------ Query by Class (pageing) -----------------------------------------------
//
//    public <T> Collection<T> getByClass(Class<T> clazz, Integer page, Integer offset) {
//        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    public <T> Collection<T> getByClassAndSpeciesTaxId(Class<T> clazz, String taxId, Integer page, Integer offset) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        return (Collection<T>)  neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    public Integer countByClassAndSpeciesTaxId(Class clazz, String taxId) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS num";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        return (Integer) neo4jTemplate.query(query, map).iterator().next().get("num");
//    }
//
//
//    public <T> Collection<T> getByClassAndSpeciesName(Class<T> clazz, String speciesName, Integer page, Integer offset) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        return (Collection<T>)  neo4jTemplate.queryForObjects(clazz, query, map);
//    }
//
//    public Integer countByClassAndSpeciesName(Class clazz, String speciesName) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS num";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        return (Integer) neo4jTemplate.query(query, map).iterator().next().get("num");
//    }
//
//
//    // ---------------------------------------- Query by Class for SimpleObject ------------------------------------------------
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz) {
//        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
//        Result result = neo4jTemplate.query(query, Collections.emptyMap());
//        return parseResult(result);
//    }
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
//    }
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
//    }
//
//    // ---------------------------------------- Query by Class for SimpleObject (paging) ------------------------------------------------
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Integer page, Integer offset) {
//        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
//    }
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId, Integer page, Integer offset) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
//    }
//
//    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName, Integer page, Integer offset) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
//    }
//
//    // ---------------------------------------- Query by Class for SimpleReferenceObject ------------------------------------------------
//
//    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz) {
//        String query = "Match (n:" + clazz.getSimpleName() + ") RETURN n.dbId AS dbId, n.databaseName AS databaseName, n.identifier AS identifier ORDER BY n.identifier ";
//        Result result = neo4jTemplate.query(query, Collections.emptyMap());
//        return parseReferenceResult(result);
//    }
//
//    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz, Integer page, Integer offset) {
//        String query = "Match (n:" + clazz.getSimpleName() + ") RETURN n.dbId AS dbId, n.databaseName AS databaseName, n.identifier AS identifier ORDER BY n.identifier SKIP {skip} LIMIT {limit}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("limit", offset);
//        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseReferenceResult(result);
//    }
//
//    // ---------------------------------------- Query by Class for ids ------------------------------------------------
//
//    public Collection<String> getStIdsByClass (Class clazz) {
//        String query = "Match (n:" + clazz.getSimpleName() + ") RETURN n.stId";
//        return (Collection<String>) neo4jTemplate.queryForObjects(String.class, query, Collections.emptyMap());
//    }
//
//    public Collection<Long> getDbIdsByClass (Class clazz) {
//        String query = "Match (n:" + clazz.getSimpleName() + ") RETURN n.dbId";
//        return (Collection<Long>) neo4jTemplate.queryForObjects(Long.class, query, Collections.emptyMap());
//    }
//
//    // ---------------------------------------- Count by Class ------------------------------------------------
//
//    public Long countEntries(Class<?> clazz) {
//        return neo4jTemplate.count(clazz);
//    }
//
//    public Long countEntriesWithSpeciesTaxId(Class<?> clazz, String taxId) {
//        String query = "MATCH (s:Species{taxId:{taxId}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("taxId", taxId);
//        Result result = neo4jTemplate.query(query, map);
//        return (result.iterator().hasNext()) ? ((Integer) result.iterator().next().get("n")).longValue() : null;
//    }
//
//    public Long countEntriesWithSpeciesName(Class<?> clazz, String speciesName) {
//        String query = "MATCH (s:Species{displayName:{speciesName}})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS n";
//        Map<String,Object> map = new HashMap<>();
//        map.put("speciesName", speciesName);
//        Result result = neo4jTemplate.query(query, map);
//        return (result.iterator().hasNext()) ? ((Integer) result.iterator().next().get("n")).longValue() : null;
//    }
//
//    // ---------------------------------------- private methods ------------------------------------------------
//
//    private Collection<SimpleDatabaseObject> parseResult(Result result) {
//        Collection<SimpleDatabaseObject> simpleDatabaseObjects = new ArrayList<>();
//        for (Map<String, Object> stringObjectMap : result) {
//            SimpleDatabaseObject simpleDatabaseObject = new SimpleDatabaseObject();
//            simpleDatabaseObject.setDbId(((Integer) stringObjectMap.get("dbId")).longValue());
//            simpleDatabaseObject.setStId((String) stringObjectMap.get("stId"));
//            simpleDatabaseObject.setDisplayName((String) stringObjectMap.get("displayName"));
//            simpleDatabaseObject.setLabels(Arrays.asList((String[]) stringObjectMap.get("labels")));
//            simpleDatabaseObjects.add(simpleDatabaseObject);
//        }
//        return simpleDatabaseObjects;
//    }
//
//    private Collection<SimpleReferenceObject> parseReferenceResult(Result result) {
//        Collection<SimpleReferenceObject> simpleReferenceObjects = new ArrayList<>();
//        for (Map<String, Object> stringObjectMap : result) {
//            SimpleReferenceObject simpleReferenceObject = new SimpleReferenceObject();
//            simpleReferenceObject.setDbId(((Integer) stringObjectMap.get("dbId")).longValue());
//            simpleReferenceObject.setDatabaseName((String) stringObjectMap.get("databaseName"));
//            simpleReferenceObject.setIdentifier((String) stringObjectMap.get("identifier"));
//            simpleReferenceObjects.add(simpleReferenceObject);
//        }
//        return simpleReferenceObjects;
//    }
//}
