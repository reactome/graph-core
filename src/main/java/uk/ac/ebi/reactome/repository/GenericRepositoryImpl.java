package uk.ac.ebi.reactome.repository;

import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
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
 * @since 11.11.15.
 */
@Repository
public class GenericRepositoryImpl implements GenericRepository {

    @Autowired
    private Session session;

    @Autowired
    private Neo4jOperations neo4jTemplate;

    @Override
    public <T> T findByPropertyIncludingSecondSteps(String property, Object value, String... relationships) {
        String query = "MATCH (n:DatabaseObject{";
        query += property + ":{" + property + "}})-[r]-(x) " +
                "OPTIONAL MATCH (x:CatalystActivity)-[e]->(z) " +
                "OPTIONAL MATCH (x:Regulation)-[e]-(z) " +
                "Where NOT (n)-[r";
        if (relationships != null && relationships.length > 0) {
            String pipe = ":";
            for (String relationship : relationships) {
                query += pipe + relationship;
                pipe = "|";
            }
        }
        query += "]->(m) RETURN n,r,x,e,z";
        Map<String,Object> map = new HashMap<>();
        map.put(property,value);
        Result result =  neo4jTemplate.query(query,map);
        for (Map<String, Object> stringObjectMap : result) {
            return (T) stringObjectMap.get("n");
        }
        return null;
    }

    @Override
    public <T> T findByPropertyWithRelations (String property, Object value, String... relationships) {
        String query = "MATCH (n:DatabaseObject{";
        query += property + ":{" + property + "}})-[r";
        if (relationships != null && relationships.length > 0) {
            String pipe = ":";
            for (String relationship : relationships) {
                query += pipe + relationship;
                pipe = "|";
            }
        }
        query += "]->(m) RETURN n,r,m";
        Map<String,Object> map = new HashMap<>();
        map.put(property,value);
        Result result =  neo4jTemplate.query(query,map);
        for (Map<String, Object> stringObjectMap : result) {
            return (T) stringObjectMap.get("n");
        }
        return null;
    }

    @Override
    public <T> T findByPropertyWithoutRelations (String property, Object value, String... relationships) {
        String query = "MATCH (n:DatabaseObject{";
        query += property + ":{" + property + "}})-[r]->(m) WHERE NOT (n)-[r";
        if (relationships != null && relationships.length > 0) {
            String pipe = ":";
            for (String relationship : relationships) {
                query += pipe + relationship;
                pipe = "|";
            }
        }
        query += "]->(m) RETURN n,r,m";
        Map<String,Object> map = new HashMap<>();
        map.put(property,value);
        Result result =  neo4jTemplate.query(query,map);
        for (Map<String, Object> stringObjectMap : result) {
            return (T) stringObjectMap.get("n");
        }
        return null;
    }

    @Override
    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadByProperty(clazz, property, value, depth);
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id, Integer depth) {
        return neo4jTemplate.load(clazz, id, depth);
    }

    @Override
    public <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        Collection<T> collection = session.loadAll(clazz, new Filter("dbId", dbId), depth);
        if (collection != null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth) {
        Collection<T> collection = session.loadAll(clazz, new Filter("stableIdentifier", stableIdentifier), depth);
        if (collection != null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public Long countEntries(Class<?> clazz) {
        return neo4jTemplate.count(clazz);
    }

    @Override
    public void clear() {
        neo4jTemplate.clear();
    }
}
