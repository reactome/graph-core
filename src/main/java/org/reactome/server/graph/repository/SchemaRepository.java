package org.reactome.server.graph.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public class SchemaRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Autowired
    public SchemaRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public Collection<SchemaClassCount> getSchemaClassCounts() {
        String query = "MATCH (n:DatabaseObject) RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count";
        return neo4jClient.query(query).fetchAs(SchemaClassCount.class).mappedBy((ts, rec) -> SchemaClassCount.build(rec)).all();
    }

    // ---------------------------------------- Query by Class --------------------------------------------------

    public <T> Collection<T> getByClass(Class<T> clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName";
        return neo4jTemplate.findAll(query, clazz);
    }

    public <T> Collection<T> getByClassAndSpeciesTaxId(Class<T> clazz, String taxId) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[r:species]-(n:" + clazz.getSimpleName() + ") RETURN n, collect(r), collect(s) ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return neo4jTemplate.findAll(query, map, clazz);
    }

    public <T> Collection<T> getByClassAndSpeciesName(Class<T> clazz, String speciesName) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[r:species]-(n:" + clazz.getSimpleName() + ") RETURN n, collect(r), collect(s) ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return neo4jTemplate.findAll(query, map, clazz);
    }

    // ------------------------------------ Query by Class (pageing) -----------------------------------------------

    public <T> Collection<T> getByClass(Class<T> clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jTemplate.findAll(query, map, clazz);
    }

    public <T> Collection<T> getByClassAndSpeciesTaxId(Class<T> clazz, String taxId, Integer page, Integer offset) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jTemplate.findAll(query, map, clazz);
    }

    public Integer countByClassAndSpeciesTaxId(Class clazz, String taxId) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS num";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return (int) neo4jTemplate.count(query, map);
    }


    public <T> Collection<T> getByClassAndSpeciesName(Class<T> clazz, String speciesName, Integer page, Integer offset) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN n ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jTemplate.findAll(query, map, clazz);
    }

    public Integer countByClassAndSpeciesName(Class clazz, String speciesName) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS num";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return (int)neo4jTemplate.count(query, map);
    }


    // ---------------------------------------- Query by Class for SimpleObject ------------------------------------------------

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz) {
        // TODO Test this one
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
        return neo4jClient.query(query).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return neo4jClient.query(query).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy((ts, rec) -> SimpleDatabaseObject.build(rec)).all();
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return neo4jClient.query(query).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    // ---------------------------------------- Query by Class for SimpleObject (paging) ------------------------------------------------
// TODO: more simpley databaseobject

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jClient.query(query).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId, Integer page, Integer offset) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
        return neo4jClient.query(query).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName, Integer page, Integer offset) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN Distinct(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels ORDER BY n.displayName SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
//        Result result = neo4jTemplate.query(query, map);
//        return parseResult(result);
        return neo4jClient.query(query).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }
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

    // ---------------------------------------- Query by Class for ids ------------------------------------------------

    public Collection<String> getStIdsByClass (Class clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n.stId";
        return neo4jClient.query(query).fetchAs(String.class).all();
    }

    public Collection<Long> getDbIdsByClass (Class clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n.dbId";
        return neo4jClient.query(query).fetchAs(Long.class).all();
    }

    // ---------------------------------------- Count by Class ------------------------------------------------

    public Long countEntries(Class<?> clazz) {
        return neo4jTemplate.count(clazz);
    }

    public Long countEntriesWithSpeciesTaxId(Class<?> clazz, String taxId) {
        String query = "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS n";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return neo4jTemplate.count(query, map);
    }

    public Long countEntriesWithSpeciesName(Class<?> clazz, String speciesName) {
        String query = "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") RETURN COUNT(n) AS n";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return neo4jTemplate.count(query, map);
    }

    // ---------------------------------------- private methods ------------------------------------------------
//TODO SIMPLE DATABASE OBJECT

//    private SimpleDatabaseObject parseRecord(Record record) {
//        SimpleDatabaseObject simpleDatabaseObject = new SimpleDatabaseObject();
//        simpleDatabaseObject.setDbId(record.get("dbId").asLong());
//        simpleDatabaseObject.setStId(record.get("stId").asString());
//        simpleDatabaseObject.setDisplayName(record.get("displayName").asString());
//        simpleDatabaseObject.setLabels(record.get("labels").asList(Value::asString));
//        return simpleDatabaseObject;
//    }

    private Collection<SimpleDatabaseObject> parseResult(Result result) {
        Collection<SimpleDatabaseObject> simpleDatabaseObjects = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : result) {
            SimpleDatabaseObject simpleDatabaseObject = new SimpleDatabaseObject();
            simpleDatabaseObject.setDbId(((Integer) stringObjectMap.get("dbId")).longValue());
            simpleDatabaseObject.setStId((String) stringObjectMap.get("stId"));
            simpleDatabaseObject.setDisplayName((String) stringObjectMap.get("displayName"));
            simpleDatabaseObject.setLabels(Arrays.asList((String[]) stringObjectMap.get("labels")));
            simpleDatabaseObjects.add(simpleDatabaseObject);
        }
        return simpleDatabaseObjects;
    }
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
}
