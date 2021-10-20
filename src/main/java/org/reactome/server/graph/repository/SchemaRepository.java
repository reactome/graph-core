package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.domain.result.SimpleReferenceObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
@Repository
public class SchemaRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public SchemaRepository(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public Collection<SchemaClassCount> getSchemaClassCounts() {
        String query = "MATCH (n:DatabaseObject) RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count";
        return neo4jClient.query(query).in(databaseName).fetchAs(SchemaClassCount.class).mappedBy((ts, rec) -> SchemaClassCount.build(rec)).all();
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
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName";
        return neo4jClient.query(query).in(databaseName).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId) {
        String query = "" +
                "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy((ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName) {
        String query = "" +
                "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    // ---------------------------------------- Query by Class for SimpleObject (paging) ------------------------------------------------

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClass(Class clazz, Integer page, Integer offset) {
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName " +
                "SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesTaxId(Class clazz, String taxId, Integer page, Integer offset) {
        String query = "" +
                "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName " +
                "SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    public Collection<SimpleDatabaseObject> getSimpleDatabaseObjectByClassAndSpeciesName(Class clazz, String speciesName, Integer page, Integer offset) {
        String query = "" +
                "MATCH (s:Species{displayName:$speciesName})<-[:species]-(n:" + clazz.getSimpleName() + ") " +
                "RETURN DISTINCT(n.dbId) as dbId, n.stId as stId, n.displayName as displayName, labels(n) as labels " +
                "ORDER BY n.displayName " +
                "SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleDatabaseObject.class).mappedBy( (ts, rec) -> SimpleDatabaseObject.build(rec)).all();
    }

    // ---------------------------------------- Query by Class for SimpleReferenceObject ------------------------------------------------

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz) {
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "RETURN n.dbId AS dbId, n.databaseName AS databaseName, n.identifier AS identifier " +
                "ORDER BY n.identifier ";
        return neo4jClient.query(query).in(databaseName).fetchAs(SimpleReferenceObject.class).mappedBy( (ts, rec) -> SimpleReferenceObject.build(rec)).all();
    }

    public Collection<SimpleReferenceObject> getSimpleReferencesObjectsByClass(Class clazz, Integer page, Integer offset) {
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "RETURN n.dbId AS dbId, n.databaseName AS databaseName, n.identifier AS identifier " +
                "ORDER BY n.identifier " +
                "SKIP $skip LIMIT $limit";
        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return neo4jClient.query(query).in(databaseName).bindAll(map).fetchAs(SimpleReferenceObject.class).mappedBy( (ts, rec) -> SimpleReferenceObject.build(rec)).all();
    }

    // ---------------------------------------- Query by Class for ids ------------------------------------------------

    public Collection<String> getStIdsByClass (Class clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n.stId";
        return neo4jClient.query(query).in(databaseName).fetchAs(String.class).all();
    }

    public Collection<Long> getDbIdsByClass (Class clazz) {
        String query = "MATCH (n:" + clazz.getSimpleName() + ") RETURN n.dbId";
        return neo4jClient.query(query).in(databaseName).fetchAs(Long.class).all();
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
}
